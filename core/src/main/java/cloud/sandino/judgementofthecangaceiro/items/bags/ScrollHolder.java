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

package cloud.sandino.judgementofthecangaceiro.items.bags;

import cloud.sandino.judgementofthecangaceiro.items.ArcaneResin;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.Stylus;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.Scroll;
import cloud.sandino.judgementofthecangaceiro.items.spells.BeaconOfReturning;
import cloud.sandino.judgementofthecangaceiro.items.spells.Spell;
import cloud.sandino.judgementofthecangaceiro.journal.Notes;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;

public class ScrollHolder extends Bag {

	{
		image = ItemSpriteSheet.HOLDER;
	}

	@Override
	public boolean canHold( Item item ) {
		if (item instanceof Scroll || item instanceof Spell
				|| item instanceof ArcaneResin || item instanceof Stylus){
			return super.canHold(item);
		} else {
			return false;
		}
	}

	public int capacity(){
		return 19;
	}
	
	@Override
	public void onDetach( ) {
		super.onDetach();
		for (Item item : items) {
			if (item instanceof BeaconOfReturning && ((BeaconOfReturning) item).returnDepth != -1) {
				Notes.remove(Notes.Landmark.BEACON_LOCATION, ((BeaconOfReturning) item).returnDepth);
				((BeaconOfReturning) item).returnDepth = -1;
			}
		}
	}
	
	@Override
	public int value() {
		return 40;
	}

}
