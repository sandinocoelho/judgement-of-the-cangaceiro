/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs;

import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.Actor;
import cloud.sandino.judgementofthecangaceiro.actors.Char;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.CorrosiveGas;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.ToxicGas;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.AllyBuff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Burning;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Invisibility;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.actors.hero.HeroSubClass;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.BodyForm;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.HolyWeapon;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Mob;
import cloud.sandino.judgementofthecangaceiro.items.rings.RingOfAccuracy;
import cloud.sandino.judgementofthecangaceiro.items.rings.RingOfEvasion;
import cloud.sandino.judgementofthecangaceiro.items.weapon.Weapon;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.WornShortsword;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.sprites.CharSprite;
import cloud.sandino.judgementofthecangaceiro.sprites.MirrorSprite;
import cloud.sandino.judgementofthecangaceiro.ui.BuffIndicator;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class MirrorImage extends NPC {
	
	{
		spriteClass = MirrorSprite.class;
		
		HP = HT = 1;
		defenseSkill = 1;
		
		alignment = Alignment.ALLY;
		state = HUNTING;
		
		//before other mobs
		actPriority = MOB_PRIO + 1;
	}
	
	private Hero hero;
	private int heroID;
	public int armTier;
	
	@Override
	protected boolean act() {
		
		if ( hero == null ){
			hero = (Hero)Actor.findById(heroID);
			if ( hero == null ){
				die(null);
				sprite.killAndErase();
				return true;
			}
		}
		
		if (hero.tier() != armTier){
			armTier = hero.tier();
			((MirrorSprite)sprite).updateArmor( armTier );
		}
		
		return super.act();
	}
	
	private static final String HEROID	= "hero_id";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( HEROID, heroID );
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		heroID = bundle.getInt( HEROID );
	}
	
	public void duplicate( Hero hero ) {
		this.hero = hero;
		heroID = this.hero.id();
		Buff.affect(this, MirrorInvis.class, Short.MAX_VALUE);
	}
	
	@Override
	public int damageRoll() {
		int damage;
		if (hero.belongings.weapon() != null){
			damage = hero.belongings.weapon().damageRoll(this);
		} else {
			damage = hero.damageRoll(); //handles ring of force
		}
		return (damage+1)/2; //half hero damage, rounded up
	}
	
	@Override
	public int attackSkill( Char target ) {
		//same base attack skill as hero, benefits from accuracy ring and weapon
		int attackSkill = 9 + hero.lvl;
		attackSkill *= RingOfAccuracy.accuracyMultiplier(hero);
		if (hero.belongings.attackingWeapon() != null){
			attackSkill *= hero.belongings.attackingWeapon().accuracyFactor(this, target);
		}
		return attackSkill;
	}
	
	@Override
	public int defenseSkill(Char enemy) {
		if (hero != null) {
			int baseEvasion = 4 + hero.lvl;
			int heroEvasion = (int)((4 + hero.lvl) * RingOfEvasion.evasionMultiplier( hero ));
			
			//if the hero has more/less evasion, 50% of it is applied
			//includes ring of evasion boost
			return super.defenseSkill(enemy) * (baseEvasion + heroEvasion) / 2;
		} else {
			return 0;
		}
	}
	
	@Override
	public float attackDelay() {
		return hero.attackDelay(); //handles ring of furor
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return super.canAttack(enemy) || (hero.belongings.weapon() != null && hero.belongings.weapon().canReach(this, enemy.pos));
	}
	
	@Override
	public int drRoll() {
		int dr = super.drRoll();
		if (hero != null && hero.belongings.weapon() != null){
			return dr + Random.NormalIntRange(0, hero.belongings.weapon().defenseFactor(this)/2);
		} else {
			return dr;
		}
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		
		MirrorInvis buff = buff(MirrorInvis.class);
		if (buff != null){
			buff.detach();
		}
		
		if (enemy instanceof Mob) {
			((Mob)enemy).aggro( this );
		}
		if (hero.belongings.weapon() != null){
			damage = hero.belongings.weapon().proc( this, enemy, damage );
			if (!enemy.isAlive() && enemy == Dungeon.hero){
				Dungeon.fail(this);
				GLog.n( Messages.capitalize(Messages.get(Char.class, "kill", name())) );
			}
			return damage;
		} else {
			//hero benefits from holy weapon and body form when unarmed, so do mirror images
			boolean wasEnemy = enemy.alignment == Alignment.ENEMY;
			if (hero.buff(BodyForm.BodyFormBuff.class) != null
					&& hero.buff(BodyForm.BodyFormBuff.class).enchant() != null){
				damage = hero.buff(BodyForm.BodyFormBuff.class).enchant().proc(new WornShortsword(), this, enemy, damage);
			}
			if (!wasEnemy || enemy.alignment == Alignment.ENEMY) {
				if (hero.buff(HolyWeapon.HolyWepBuff.class) != null) {
					int dmg = hero.subClass == HeroSubClass.PALADIN ? 6 : 2;
					enemy.damage(Math.round(dmg * Weapon.Enchantment.genericProcChanceMultiplier(this)), HolyWeapon.INSTANCE);
				}
			}
			return damage;
		}
	}
	
	@Override
	public CharSprite sprite() {
		CharSprite s = super.sprite();
		
		hero = (Hero)Actor.findById(heroID);
		if (hero != null) {
			armTier = hero.tier();
		} else {
			armTier = 1;
		}
		((MirrorSprite)s).updateArmor( armTier );
		return s;
	}
	
	{
		immunities.add( ToxicGas.class );
		immunities.add( CorrosiveGas.class );
		immunities.add( Burning.class );
		immunities.add( AllyBuff.class );
	}
	
	public static class MirrorInvis extends Invisibility {
		
		{
			announced = false;
		}
		
		@Override
		public int icon() {
			return BuffIndicator.NONE;
		}
	}
}