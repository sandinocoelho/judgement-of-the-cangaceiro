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

package cloud.sandino.judgementofthecangaceiro.actors.mobs;

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.Actor;
import cloud.sandino.judgementofthecangaceiro.effects.CellEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.Speck;
import cloud.sandino.judgementofthecangaceiro.items.EquipableItem;
import cloud.sandino.judgementofthecangaceiro.items.Generator;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.armor.Armor;
import cloud.sandino.judgementofthecangaceiro.items.artifacts.Artifact;
import cloud.sandino.judgementofthecangaceiro.items.wands.Wand;
import cloud.sandino.judgementofthecangaceiro.items.weapon.Weapon;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.MissileWeapon;
import cloud.sandino.judgementofthecangaceiro.levels.Terrain;
import cloud.sandino.judgementofthecangaceiro.levels.features.Door;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.sprites.MimicSprite;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class EbonyMimic extends Mimic {

	{
		spriteClass = MimicSprite.Ebony.class;
	}

	@Override
	public String name() {
		if (alignment == Alignment.NEUTRAL){
			return Messages.get(this, "hidden_name");
		} else {
			return super.name();
		}
	}

	@Override
	public String description() {
		if (alignment == Alignment.NEUTRAL){
			return Messages.get(this, "hidden_desc");
		} else {
			return super.description();
		}
	}

	@Override
	public boolean stealthy() {
		return true;
	}

	public void stopHiding(){
		state = HUNTING;
		if (sprite != null) sprite.idle();
		if (Actor.chars().contains(this) && Dungeon.level.heroFOV[pos]) {
			enemy = Dungeon.hero;
			target = Dungeon.hero.pos;
			GLog.w(Messages.get(this, "reveal") );
			CellEmitter.get(pos).burst(Speck.factory(Speck.STAR), 10);
			Sample.INSTANCE.play(Assets.Sounds.MIMIC, 1, 0.85f);
		}
		if (Actor.chars().contains(this) && Dungeon.level.map[pos] == Terrain.DOOR){
			Door.enter( pos );
		}
	}

	@Override
	public int damageRoll() {
		if (alignment == Alignment.NEUTRAL){
			return Math.round(super.damageRoll()*2f); //BIG damage on surprise
		} else {
			return super.damageRoll();
		}
	}

	@Override
	protected void generatePrize( boolean useDecks ) {
		super.generatePrize( useDecks );
		//add one extra random loot item, on top of the one granted by mimic tooth
		items.add(Generator.randomUsingDefaults());

		//all existing prize items are guaranteed uncursed, and are always at least +1
		for (Item i : items){
			if (i instanceof EquipableItem || i instanceof Wand){
				i.cursed = false;
				i.cursedKnown = true;
				if (i instanceof Weapon && ((Weapon) i).hasCurseEnchant()){
					((Weapon) i).enchant(null);
				}
				if (i instanceof Armor && ((Armor) i).hasCurseGlyph()){
					((Armor) i).inscribe(null);
				}
				if (!(i instanceof Artifact) && i.level() == 0){
					i.upgrade();
				}
			}
		}
	}

}
