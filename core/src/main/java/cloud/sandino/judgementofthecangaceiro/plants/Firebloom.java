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

package cloud.sandino.judgementofthecangaceiro.plants;

import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.Char;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Blob;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Fire;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.FireImbue;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.actors.hero.HeroSubClass;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Mob;
import cloud.sandino.judgementofthecangaceiro.effects.CellEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.particles.FlameParticle;
import cloud.sandino.judgementofthecangaceiro.levels.traps.Trap;
import cloud.sandino.judgementofthecangaceiro.scenes.GameScene;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;

public class Firebloom extends Plant {
	
	{
		image = 1;
		seedClass = Seed.class;
	}
	
	@Override
	public void activate( Char ch ) {
		
		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN){
			Buff.affect(ch, FireImbue.class).set( FireImbue.DURATION*0.3f );
		}

		if (ch instanceof Mob){
			Buff.prolong(ch, Trap.HazardAssistTracker.class, Trap.HazardAssistTracker.DURATION);
		}
		
		GameScene.add( Blob.seed( pos, 2, Fire.class ) );
		
		if (Dungeon.level.heroFOV[pos]) {
			CellEmitter.get( pos ).burst( FlameParticle.FACTORY, 5 );
		}
	}
	
	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_FIREBLOOM;

			plantClass = Firebloom.class;
		}
	}
}
