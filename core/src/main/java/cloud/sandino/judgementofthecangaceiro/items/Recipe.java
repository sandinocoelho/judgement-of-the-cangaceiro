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

package cloud.sandino.judgementofthecangaceiro.items;

import cloud.sandino.judgementofthecangaceiro.ShatteredPixelDungeon;
import cloud.sandino.judgementofthecangaceiro.items.bombs.Bomb;
import cloud.sandino.judgementofthecangaceiro.items.food.Blandfruit;
import cloud.sandino.judgementofthecangaceiro.items.food.MeatPie;
import cloud.sandino.judgementofthecangaceiro.items.food.StewedMeat;
import cloud.sandino.judgementofthecangaceiro.items.potions.Potion;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.AquaBrew;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.BlizzardBrew;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.CausticBrew;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.InfernalBrew;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.ShockingBrew;
import cloud.sandino.judgementofthecangaceiro.items.potions.brews.UnstableBrew;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfAquaticRejuvenation;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfArcaneArmor;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfDragonsBlood;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfFeatherFall;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfHoneyedHealing;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfIcyTouch;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfMight;
import cloud.sandino.judgementofthecangaceiro.items.potions.elixirs.ElixirOfToxicEssence;
import cloud.sandino.judgementofthecangaceiro.items.potions.exotic.ExoticPotion;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.Scroll;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.exotic.ExoticScroll;
import cloud.sandino.judgementofthecangaceiro.items.spells.Alchemize;
import cloud.sandino.judgementofthecangaceiro.items.spells.BeaconOfReturning;
import cloud.sandino.judgementofthecangaceiro.items.spells.CurseInfusion;
import cloud.sandino.judgementofthecangaceiro.items.spells.MagicalInfusion;
import cloud.sandino.judgementofthecangaceiro.items.spells.PhaseShift;
import cloud.sandino.judgementofthecangaceiro.items.spells.ReclaimTrap;
import cloud.sandino.judgementofthecangaceiro.items.spells.Recycle;
import cloud.sandino.judgementofthecangaceiro.items.spells.SummonElemental;
import cloud.sandino.judgementofthecangaceiro.items.spells.TelekineticGrab;
import cloud.sandino.judgementofthecangaceiro.items.spells.UnstableSpell;
import cloud.sandino.judgementofthecangaceiro.items.spells.WildEnergy;
import cloud.sandino.judgementofthecangaceiro.items.trinkets.Trinket;
import cloud.sandino.judgementofthecangaceiro.items.trinkets.TrinketCatalyst;
import cloud.sandino.judgementofthecangaceiro.items.wands.Wand;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.MissileWeapon;
import com.watabou.utils.Reflection;

import java.util.ArrayList;

public abstract class Recipe {
	
	public abstract boolean testIngredients(ArrayList<Item> ingredients);
	
	public abstract int cost(ArrayList<Item> ingredients);
	
	public abstract Item brew(ArrayList<Item> ingredients);
	
	public abstract Item sampleOutput(ArrayList<Item> ingredients);
	
	//subclass for the common situation of a recipe with static inputs and outputs
	public static abstract class SimpleRecipe extends Recipe {
		
		//*** These elements must be filled in by subclasses
		protected Class<?extends Item>[] inputs; //each class should be unique
		protected int[] inQuantity;
		
		protected int cost;
		
		protected Class<?extends Item> output;
		protected int outQuantity;
		//***
		
		//gets a simple list of items based on inputs
		public ArrayList<Item> getIngredients() {
			ArrayList<Item> result = new ArrayList<>();
			for (int i = 0; i < inputs.length; i++) {
				Item ingredient = Reflection.newInstance(inputs[i]);
				ingredient.quantity(inQuantity[i]);
				result.add(ingredient);
			}
			return result;
		}
		
		@Override
		public boolean testIngredients(ArrayList<Item> ingredients) {
			
			int[] needed = inQuantity.clone();
			
			for (Item ingredient : ingredients){
				if (!ingredient.isIdentified()) return false;
				for (int i = 0; i < inputs.length; i++){
					if (ingredient.getClass() == inputs[i]){
						needed[i] -= ingredient.quantity();
						break;
					}
				}
			}
			
			for (int i : needed){
				if (i > 0){
					return false;
				}
			}
			
			return true;
		}
		
		public int cost(ArrayList<Item> ingredients){
			return cost;
		}
		
		@Override
		public Item brew(ArrayList<Item> ingredients) {
			if (!testIngredients(ingredients)) return null;
			
			int[] needed = inQuantity.clone();
			
			for (Item ingredient : ingredients){
				for (int i = 0; i < inputs.length; i++) {
					if (ingredient.getClass() == inputs[i] && needed[i] > 0) {
						if (needed[i] <= ingredient.quantity()) {
							ingredient.quantity(ingredient.quantity() - needed[i]);
							needed[i] = 0;
						} else {
							needed[i] -= ingredient.quantity();
							ingredient.quantity(0);
						}
					}
				}
			}
			
			//sample output and real output are identical in this case.
			return sampleOutput(null);
		}
		
		//ingredients are ignored, as output doesn't vary
		public Item sampleOutput(ArrayList<Item> ingredients){
			try {
				Item result = Reflection.newInstance(output);
				result.quantity(outQuantity);
				return result;
			} catch (Exception e) {
				ShatteredPixelDungeon.reportException( e );
				return null;
			}
		}
	}
	
	
	//*******
	// Static members
	//*******

	private static Recipe[] variableRecipes = new Recipe[]{
			//none for now
	};
	
	private static Recipe[] oneIngredientRecipes = new Recipe[]{
		new Scroll.ScrollToStone(),
		new ExoticPotion.PotionToExotic(),
		new ExoticScroll.ScrollToExotic(),
		new ArcaneResin.Recipe(),
		new LiquidMetal.Recipe(),
		new BlizzardBrew.Recipe(),
		new InfernalBrew.Recipe(),
		new AquaBrew.Recipe(),
		new ShockingBrew.Recipe(),
		new ElixirOfDragonsBlood.Recipe(),
		new ElixirOfIcyTouch.Recipe(),
		new ElixirOfToxicEssence.Recipe(),
		new ElixirOfMight.Recipe(),
		new ElixirOfFeatherFall.Recipe(),
		new MagicalInfusion.Recipe(),
		new BeaconOfReturning.Recipe(),
		new PhaseShift.Recipe(),
		new Recycle.Recipe(),
		new TelekineticGrab.Recipe(),
		new SummonElemental.Recipe(),
		new StewedMeat.oneMeat(),
		new TrinketCatalyst.Recipe(),
		new Trinket.UpgradeTrinket()
	};
	
	private static Recipe[] twoIngredientRecipes = new Recipe[]{
		new Blandfruit.CookFruit(),
		new Bomb.EnhanceBomb(),
		new UnstableBrew.Recipe(),
		new CausticBrew.Recipe(),
		new ElixirOfArcaneArmor.Recipe(),
		new ElixirOfAquaticRejuvenation.Recipe(),
		new ElixirOfHoneyedHealing.Recipe(),
		new UnstableSpell.Recipe(),
		new Alchemize.Recipe(),
		new CurseInfusion.Recipe(),
		new ReclaimTrap.Recipe(),
		new WildEnergy.Recipe(),
		new StewedMeat.twoMeat()
	};
	
	private static Recipe[] threeIngredientRecipes = new Recipe[]{
		new Potion.SeedToPotion(),
		new StewedMeat.threeMeat(),
		new MeatPie.Recipe()
	};
	
	public static ArrayList<Recipe> findRecipes(ArrayList<Item> ingredients){

		ArrayList<Recipe> result = new ArrayList<>();

		for (Recipe recipe : variableRecipes){
			if (recipe.testIngredients(ingredients)){
				result.add(recipe);
			}
		}

		if (ingredients.size() == 1){
			for (Recipe recipe : oneIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
			
		} else if (ingredients.size() == 2){
			for (Recipe recipe : twoIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
			
		} else if (ingredients.size() == 3){
			for (Recipe recipe : threeIngredientRecipes){
				if (recipe.testIngredients(ingredients)){
					result.add(recipe);
				}
			}
		}
		
		return result;
	}
	
	public static boolean usableInRecipe(Item item){
		//only upgradeable thrown weapons and wands allowed among equipment items
		if (item instanceof EquipableItem){
			return item.cursedKnown && !item.cursed &&
					item instanceof MissileWeapon && item.isUpgradable();
		} else if (item instanceof Wand) {
			return item.cursedKnown && !item.cursed;
		} else {
			//other items can be unidentified, but not cursed
			return !item.cursed;
		}
	}
}


