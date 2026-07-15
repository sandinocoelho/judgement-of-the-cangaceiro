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

package cloud.sandino.judgementofthecangaceiro.actors.blobs;

import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Shadows;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.effects.BlobEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.particles.ShaftParticle;
import cloud.sandino.judgementofthecangaceiro.journal.Notes;
import cloud.sandino.judgementofthecangaceiro.levels.Level;
import cloud.sandino.judgementofthecangaceiro.levels.Terrain;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.scenes.GameScene;
import com.watabou.utils.PathFinder;

public class Foliage extends Blob {

	@Override
	public Notes.Landmark landmark() {
		return Notes.Landmark.GARDEN;
	}

	@Override
	protected void evolve() {

		int[] map = Dungeon.level.map;
		
		boolean seen = false;

		Fire fire = (Fire)Dungeon.level.blobs.get( Fire.class );

		int cell;
		for (int i = area.left; i < area.right; i++) {
			for (int j = area.top; j < area.bottom; j++) {
				cell = i + j*Dungeon.level.width();
				if (cur[cell] > 0) {

					off[cell] = cur[cell];
					volume += off[cell];

					if (map[cell] == Terrain.EMBERS) {
						//only turn terrain into grass if no fire is adjacent to it
						boolean valid = true;
						if (fire != null && fire.volume > 0) {
							for (int k : PathFinder.NEIGHBOURS9) {
								if (fire.cur[cell + k] > 0){
									valid = false;
								}
							}
						}
						if (valid) {
							Level.set(cell, Terrain.GRASS);
							GameScene.updateMap(cell);
						}
					}

					seen = seen || Dungeon.level.visited[cell];

				} else {
					off[cell] = 0;
				}
			}
		}
		
		Hero hero = Dungeon.hero;
		if (hero.isAlive() && cur[hero.pos] > 0) {
			Shadows s = Buff.affect( hero, Shadows.class );
			if (s != null){
				s.prolong();
			}
		}
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( ShaftParticle.FACTORY, 0.9f, 0 );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
