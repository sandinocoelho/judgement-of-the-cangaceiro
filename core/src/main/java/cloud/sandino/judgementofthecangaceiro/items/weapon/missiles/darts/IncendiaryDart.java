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

package cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.darts;

import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.Actor;
import cloud.sandino.judgementofthecangaceiro.actors.Char;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Blob;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Fire;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Burning;
import cloud.sandino.judgementofthecangaceiro.scenes.GameScene;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;

public class IncendiaryDart extends TippedDart {

	{
		image = ItemSpriteSheet.INCENDIARY_DART;
	}
	
	@Override
	protected void onThrow( int cell ) {
		Char enemy = Actor.findChar( cell );
		if ((enemy == null || enemy == curUser) && Dungeon.level.flamable[cell]) {
			GameScene.add(Blob.seed(cell, 4, Fire.class));
			decrementDurability();
			if (durability > 0 || spawnedForEffect){
				super.onThrow(cell);
			} else {
				Dungeon.level.drop(new Dart().quantity(1), cell).sprite.drop();
			}
		} else{
			super.onThrow(cell);
		}
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		//when processing charged shot, only burn enemies
		if (!processingChargedShot || attacker.alignment != defender.alignment) {
			Buff.affect(defender, Burning.class).reignite(defender);
		}
		return super.proc( attacker, defender, damage );
	}
	
}
