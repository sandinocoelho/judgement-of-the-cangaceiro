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
import cloud.sandino.judgementofthecangaceiro.actors.Char;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Roots;
import cloud.sandino.judgementofthecangaceiro.effects.BlobEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.particles.WebParticle;
import cloud.sandino.judgementofthecangaceiro.levels.Level;
import cloud.sandino.judgementofthecangaceiro.levels.Terrain;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;

public class Web extends Blob {

	{
		//acts before the hero, to ensure terrain is adjusted correctly
		actPriority = HERO_PRIO+1;
	}
	
	@Override
	protected void evolve() {

		int cell;

		Level l = Dungeon.level;
		for (int i = area.left; i < area.right; i++){
			for (int j = area.top; j < area.bottom; j++){
				cell = i + j*l.width();
				off[cell] = cur[cell] > 0 ? cur[cell] - 1 : 0;

				volume += off[cell];

				if (off[cell] == 0 && cur[cell] > 0){
					cellsToFlagUpdate.add(cell);
				}
			}
		}
	}

	@Override
	public void seed(Level level, int cell, int amount) {
		super.seed(level, cell, amount);
		level.updateCellFlags(cell);
	}

	//affects characters as they step on it. See Level.OccupyCell and Level.PressCell
	public static void affectChar( Char ch ){
		Buff.prolong( ch, Roots.class, Roots.DURATION );
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		
		emitter.pour( WebParticle.FACTORY, 0.25f );
	}

	@Override
	public void clear(int cell) {
		super.clear(cell);
		if (cur == null) return;
		Dungeon.level.updateCellFlags(cell);
	}

	@Override
	public void fullyClear() {
		super.fullyClear();
		Dungeon.level.buildFlagMaps();
	}

	@Override
	public void onBuildFlagMaps(Level l) {
		if (volume > 0){
			for (int i=0; i < l.length(); i++) {
				onUpdateCellFlags(l, i);
			}
		}
	}

	@Override
	public void onUpdateCellFlags(Level l, int cell) {
		if (volume > 0 && cur[cell] > 0) {
			l.solid[cell] = true;
			l.flamable[cell] = true;
			//openSpace will be updated as part of updating flags in Level
		}
	}

	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
