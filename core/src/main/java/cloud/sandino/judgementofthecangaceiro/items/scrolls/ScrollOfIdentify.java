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

package cloud.sandino.judgementofthecangaceiro.items.scrolls;

import cloud.sandino.judgementofthecangaceiro.Badges;
import cloud.sandino.judgementofthecangaceiro.effects.Identification;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.armor.Armor;
import cloud.sandino.judgementofthecangaceiro.items.rings.Ring;
import cloud.sandino.judgementofthecangaceiro.items.trinkets.ShardOfOblivion;
import cloud.sandino.judgementofthecangaceiro.items.wands.Wand;
import cloud.sandino.judgementofthecangaceiro.items.weapon.Weapon;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;

public class ScrollOfIdentify extends InventoryScroll {

	{
		icon = ItemSpriteSheet.Icons.SCROLL_IDENTIFY;

		bones = true;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return !item.isIdentified();
	}

	@Override
	protected void onItemSelected( Item item ) {
		
		curUser.sprite.parent.add( new Identification( curUser.sprite.center().offset( 0, -16 ) ) );

		IDItem(item);
	}

	public static void IDItem( Item item ){
		if (ShardOfOblivion.passiveIDDisabled()) {
			if (item instanceof Weapon){
				((Weapon) item).setIDReady();
				GLog.p(Messages.get(ShardOfOblivion.class, "identify_ready"), item.name());
				return;
			} else if (item instanceof Armor){
				((Armor) item).setIDReady();
				GLog.p(Messages.get(ShardOfOblivion.class, "identify_ready"), item.name());
				return;
			} else if (item instanceof Ring){
				((Ring) item).setIDReady();
				GLog.p(Messages.get(ShardOfOblivion.class, "identify_ready"), item.name());
				return;
			} else if (item instanceof Wand){
				((Wand) item).setIDReady();
				GLog.p(Messages.get(ShardOfOblivion.class, "identify_ready"), item.name());
				return;
			}
		}

		item.identify();
		GLog.i(Messages.get(ScrollOfIdentify.class, "it_is", item.title()));
		Badges.validateItemLevelAquired( item );
	}
	
	@Override
	public int value() {
		return isKnown() ? 30 * quantity : super.value();
	}
}
