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

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.ArtifactRecharge;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Recharging;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.effects.SpellSprite;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.quest.MetalShard;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfRecharging;
import cloud.sandino.judgementofthecangaceiro.items.wands.CursedWand;
import cloud.sandino.judgementofthecangaceiro.journal.Catalog;
import cloud.sandino.judgementofthecangaceiro.mechanics.Ballistica;
import cloud.sandino.judgementofthecangaceiro.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class WildEnergy extends TargetedSpell {
	
	{
		image = ItemSpriteSheet.WILD_ENERGY;

		usesTargeting = true;

		talentChance = 1/(float)Recipe.OUT_QUANTITY;
	}
	
	//we rely on cursedWand to do fx instead
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		CursedWand.cursedZap(this, curUser, bolt, callback);
	}
	
	@Override
	protected void affectTarget(Ballistica bolt, final Hero hero) {
		Sample.INSTANCE.play( Assets.Sounds.LIGHTNING );
		Sample.INSTANCE.play( Assets.Sounds.CHARGEUP );
		ScrollOfRecharging.charge(hero);
		SpellSprite.show(hero, SpellSprite.CHARGE);

		hero.belongings.charge(1f);
		ArtifactRecharge.chargeArtifacts(hero, 4f);

		Buff.affect(hero, Recharging.class, 8f);
		Buff.affect(hero, ArtifactRecharge.class).extend( 8 ).ignoreHornOfPlenty = false;

		onSpellused();
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

		private static final int OUT_QUANTITY = 5;
		
		{
			inputs =  new Class[]{ScrollOfRecharging.class, MetalShard.class};
			inQuantity = new int[]{1, 1};
			
			cost = 4;
			
			output = WildEnergy.class;
			outQuantity = OUT_QUANTITY;
		}

		@Override
		public Item brew(ArrayList<Item> ingredients) {
			Catalog.countUse(MetalShard.class);
			return super.brew(ingredients);
		}
	}
}
