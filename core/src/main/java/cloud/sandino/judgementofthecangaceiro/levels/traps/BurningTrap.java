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

package cloud.sandino.judgementofthecangaceiro.levels.traps;

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.Actor;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Blob;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Fire;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Mob;
import cloud.sandino.judgementofthecangaceiro.effects.CellEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.particles.FlameParticle;
import cloud.sandino.judgementofthecangaceiro.scenes.GameScene;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class BurningTrap extends Trap {

	{
		color = ORANGE;
		shape = DOTS;
	}

	@Override
	public void activate() {
		
		for( int i : PathFinder.NEIGHBOURS9) {
			if (!Dungeon.level.solid[pos + i]) {
				GameScene.add( Blob.seed( pos+i, 2, Fire.class ) );
				CellEmitter.get( pos+i ).burst( FlameParticle.FACTORY, 5 );
				if (Actor.findChar(pos+i) instanceof Mob){
					Buff.prolong(Actor.findChar(pos+i), Trap.HazardAssistTracker.class, HazardAssistTracker.DURATION);
				}
			}
		}
		Sample.INSTANCE.play(Assets.Sounds.BURNING);
	}
}
