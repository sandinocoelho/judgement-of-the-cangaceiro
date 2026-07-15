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

package cloud.sandino.judgementofthecangaceiro.items.armor.glyphs;

import cloud.sandino.judgementofthecangaceiro.actors.Char;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Charm;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Degrade;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Hex;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.MagicalSleep;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Vulnerable;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Weakness;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.duelist.ElementalStrike;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.mage.ElementalBlast;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.mage.WarpBeacon;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.GuidingLight;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.HolyLance;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.HolyWeapon;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.Judgement;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.Smite;
import cloud.sandino.judgementofthecangaceiro.actors.hero.spells.Sunray;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.CrystalWisp;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DM100;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Eye;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Shaman;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Warlock;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.YogFist;
import cloud.sandino.judgementofthecangaceiro.items.armor.Armor;
import cloud.sandino.judgementofthecangaceiro.items.artifacts.ChaliceOfBlood;
import cloud.sandino.judgementofthecangaceiro.items.bombs.ArcaneBomb;
import cloud.sandino.judgementofthecangaceiro.items.bombs.HolyBomb;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfRetribution;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfTeleportation;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.exotic.ScrollOfPsionicBlast;
import cloud.sandino.judgementofthecangaceiro.items.wands.CursedWand;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfBlastWave;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfDisintegration;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfFireblast;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfFrost;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfLightning;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfLivingEarth;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfMagicMissile;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfPrismaticLight;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfTransfusion;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfWarding;
import cloud.sandino.judgementofthecangaceiro.items.weapon.enchantments.Blazing;
import cloud.sandino.judgementofthecangaceiro.items.weapon.enchantments.Grim;
import cloud.sandino.judgementofthecangaceiro.items.weapon.enchantments.Shocking;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.darts.HolyDart;
import cloud.sandino.judgementofthecangaceiro.levels.traps.DisintegrationTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GrimTrap;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSprite;
import com.watabou.utils.Random;

import java.util.HashSet;

public class AntiMagic extends Armor.Glyph {

	private static ItemSprite.Glowing TEAL = new ItemSprite.Glowing( 0x88EEFF );
	
	public static final HashSet<Class> RESISTS = new HashSet<>();
	static {
		RESISTS.add( MagicalSleep.class );
		RESISTS.add( Charm.class );
		RESISTS.add( Weakness.class );
		RESISTS.add( Vulnerable.class );
		RESISTS.add( Hex.class );
		RESISTS.add( Degrade.class );
		
		RESISTS.add( DisintegrationTrap.class );
		RESISTS.add( GrimTrap.class );

		RESISTS.add( ArcaneBomb.class );
		RESISTS.add( HolyBomb.HolyDamage.class );
		RESISTS.add( ScrollOfRetribution.class );
		RESISTS.add( ScrollOfPsionicBlast.class );
		RESISTS.add( ScrollOfTeleportation.class );
		RESISTS.add( HolyDart.class );

		RESISTS.add( GuidingLight.class );
		RESISTS.add( HolyWeapon.class );
		RESISTS.add( Sunray.class );
		RESISTS.add( HolyLance.class );
		RESISTS.add( Smite.class );
		RESISTS.add( Judgement.class );

		RESISTS.add( ElementalBlast.class );
		RESISTS.add( CursedWand.class );
		RESISTS.add( WandOfBlastWave.class );
		RESISTS.add( WandOfDisintegration.class );
		RESISTS.add( WandOfFireblast.class );
		RESISTS.add( WandOfFrost.class );
		RESISTS.add( WandOfLightning.class );
		RESISTS.add( WandOfLivingEarth.class );
		RESISTS.add( WandOfMagicMissile.class );
		RESISTS.add( WandOfPrismaticLight.class );
		RESISTS.add( WandOfTransfusion.class );
		RESISTS.add( WandOfWarding.Ward.class );

		RESISTS.add( ChaliceOfBlood.class );

		RESISTS.add( ElementalStrike.class );
		RESISTS.add( Blazing.class );
		RESISTS.add( Shocking.class );
		RESISTS.add( Grim.class );

		RESISTS.add( WarpBeacon.class );
		
		RESISTS.add( DM100.LightningBolt.class );
		RESISTS.add( Shaman.EarthenBolt.class );
		RESISTS.add( CrystalWisp.LightBeam.class );
		RESISTS.add( Warlock.DarkBolt.class );
		RESISTS.add( Eye.DeathGaze.class );
		RESISTS.add( YogFist.BrightFist.LightBeam.class );
		RESISTS.add( YogFist.DarkFist.DarkBolt.class );
	}
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {
		//no proc effect, triggers in Char.damage
		return damage;
	}
	
	public static int drRoll( Char owner, int level ){
		if (level == -1){
			return 0;
		} else {
			return Random.NormalIntRange(
					Math.round(level * genericProcChanceMultiplier(owner)),
					Math.round((3 + (level * 1.5f)) * genericProcChanceMultiplier(owner)));
		}
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return TEAL;
	}

}