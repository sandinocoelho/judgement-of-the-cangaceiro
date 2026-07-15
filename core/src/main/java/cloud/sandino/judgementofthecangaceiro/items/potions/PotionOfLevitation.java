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

package cloud.sandino.judgementofthecangaceiro.items.potions;

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Blob;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.ConfusionGas;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Levitation;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.scenes.GameScene;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class PotionOfLevitation extends Potion {

	{
		icon = ItemSpriteSheet.Icons.POTION_LEVITATE;
	}

	@Override
	public void shatter( int cell ) {

		splash( cell );
		if (Dungeon.level.heroFOV[cell]) {
			identify();

			Sample.INSTANCE.play( Assets.Sounds.SHATTER );
			Sample.INSTANCE.play( Assets.Sounds.GAS );
		}

		GameScene.add( Blob.seed( cell, 1000, ConfusionGas.class ) );
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		Buff.prolong( hero, Levitation.class, Levitation.DURATION );
		GLog.i( Messages.get(this, "float") );
	}
	
	@Override
	public int value() {
		return isKnown() ? 40 * quantity : super.value();
	}
}
