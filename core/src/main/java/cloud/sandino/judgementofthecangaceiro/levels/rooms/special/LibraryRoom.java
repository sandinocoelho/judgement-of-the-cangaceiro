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

package cloud.sandino.judgementofthecangaceiro.levels.rooms.special;

import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.items.Generator;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.keys.IronKey;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.Scroll;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfIdentify;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfRemoveCurse;
import cloud.sandino.judgementofthecangaceiro.items.trinkets.TrinketCatalyst;
import cloud.sandino.judgementofthecangaceiro.levels.Level;
import cloud.sandino.judgementofthecangaceiro.levels.Terrain;
import cloud.sandino.judgementofthecangaceiro.levels.painters.Painter;
import com.watabou.utils.Random;

public class LibraryRoom extends SpecialRoom {

	public void paint( Level level ) {
		
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY_SP );
		
		Door entrance = entrance();
		
		Painter.fill( level, left + 1, top+1, width() - 2, 1 , Terrain.BOOKSHELF );
		Painter.drawInside(level, this, entrance, 1, Terrain.EMPTY_SP );
		
		int n = Random.NormalIntRange( 1, 3 );
		for (int i=0; i < n; i++) {
			int pos;
			do {
				pos = level.pointToCell(random());
			} while (level.map[pos] != Terrain.EMPTY_SP || level.heaps.get( pos ) != null);
			Item item;
			if (i == 0)
				item = Random.Int(2) == 0 ? new ScrollOfIdentify() : new ScrollOfRemoveCurse();
			else
				item = prize( level );
			level.drop( item, pos );
		}
		
		entrance.set( Door.Type.LOCKED );
		
		level.addItemToSpawn( new IronKey( Dungeon.depth ) );
	}
	
	private static Item prize( Level level ) {
		
		Item prize = level.findPrizeItem( TrinketCatalyst.class );
		if (prize == null){
			prize = level.findPrizeItem( Scroll.class );
			if (prize == null) {
				prize = Generator.random( Generator.Category.SCROLL );
			}
		}
		
		return prize;
	}
}
