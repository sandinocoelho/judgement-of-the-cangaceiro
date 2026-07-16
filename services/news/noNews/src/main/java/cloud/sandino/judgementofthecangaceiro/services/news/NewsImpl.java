/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * Judgement of the Cangaceiro
 * Copyright (C) 2026 Sandino M. Coelho
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

package cloud.sandino.judgementofthecangaceiro.services.news;

//no-op news backend: the fork has no news feed of its own yet, and release
// builds must not query upstream's shatteredpixel.com feed (CANGA-7). The
// launchers gate on supportsNews(), so News.service stays null and the game
// hides its news UI entirely.
public class NewsImpl {

	public static NewsService getNewsService(){
		return null;
	}

	public static boolean supportsNews(){
		return false;
	}

}
