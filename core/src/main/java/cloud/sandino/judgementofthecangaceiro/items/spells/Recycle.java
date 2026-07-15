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

package cloud.sandino.judgementofthecangaceiro.items.spells;

import cloud.sandino.judgementofthecangaceiro.Challenges;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.effects.Speck;
import cloud.sandino.judgementofthecangaceiro.effects.Transmuting;
import cloud.sandino.judgementofthecangaceiro.items.Generator;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.potions.Potion;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.Brew;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.Elixir;
import cloud.sandino.judgementofthecangaceiro.items.potions.exotic.ExoticPotion;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.Scroll;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfTransmutation;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.exotic.ExoticScroll;
import cloud.sandino.judgementofthecangaceiro.items.stones.Runestone;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.darts.TippedDart;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.plants.Plant;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;
import com.watabou.utils.Reflection;

public class Recycle extends InventorySpell {
	
	{
		image = ItemSpriteSheet.RECYCLE;

		talentFactor = 2;
		talentChance = 1/(float)Recipe.OUT_QUANTITY;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return (item instanceof Potion && !(item instanceof Elixir || item instanceof Brew)) ||
				item instanceof Scroll ||
				item instanceof Plant.Seed ||
				item instanceof Runestone ||
				item instanceof TippedDart;
	}

	@Override
	protected void onItemSelected(Item item) {
		Item result;
		do {
			if (item instanceof Potion) {
				result = Generator.randomUsingDefaults(Generator.Category.POTION);
				if (item instanceof ExoticPotion){
					result = Reflection.newInstance(ExoticPotion.regToExo.get(result.getClass()));
				}
			} else if (item instanceof Scroll) {
				result = Generator.randomUsingDefaults(Generator.Category.SCROLL);
				if (item instanceof ExoticScroll){
					result = Reflection.newInstance(ExoticScroll.regToExo.get(result.getClass()));
				}
			} else if (item instanceof Plant.Seed) {
				result = Generator.randomUsingDefaults(Generator.Category.SEED);
			} else if (item instanceof Runestone) {
				result = Generator.randomUsingDefaults(Generator.Category.STONE);
			} else {
				result = TippedDart.randomTipped(1);
			}
		} while (result.getClass() == item.getClass() || Challenges.isItemBlocked(result));
		
		item.detach(curUser.belongings.backpack);
		GLog.p(Messages.get(this, "recycled", result.name()));
		if (!result.collect()){
			Dungeon.level.drop(result, curUser.pos).sprite.drop();
		}
		Transmuting.show(curUser, item, result);
		curUser.sprite.emitter().start(Speck.factory(Speck.CHANGE), 0.2f, 10);
	}
	
	@Override
	public int value() {
		return (int)(60 * (quantity/(float)Recipe.OUT_QUANTITY));
	}

	@Override
	public int energyVal() {
		return (int)(12 * (quantity/(float)Recipe.OUT_QUANTITY));
	}
	
	public static class Recipe extends cloud.sandino.judgementofthecangaceiro.items.Recipe.SimpleRecipe {

		private static final int OUT_QUANTITY = 12;
		
		{
			inputs =  new Class[]{ScrollOfTransmutation.class};
			inQuantity = new int[]{1};
			
			cost = 12;
			
			output = Recycle.class;
			outQuantity = OUT_QUANTITY;
		}
		
	}
}
