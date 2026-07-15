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

package cloud.sandino.judgementofthecangaceiro.actors.hero.spells;

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Talent;
import cloud.sandino.judgementofthecangaceiro.effects.Identification;
import cloud.sandino.judgementofthecangaceiro.items.EquipableItem;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.artifacts.HolyTome;
import cloud.sandino.judgementofthecangaceiro.items.wands.Wand;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.ui.HeroIcon;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class HolyIntuition extends InventoryClericSpell {

	public static final HolyIntuition INSTANCE = new HolyIntuition();

	@Override
	public int icon() {
		return HeroIcon.HOLY_INTUITION;
	}

	@Override
	protected boolean usableOnItem(Item item) {
		return (item instanceof EquipableItem || item instanceof Wand) && !item.isIdentified() && !item.cursedKnown;
	}

	@Override
	public float chargeUse(Hero hero) {
		return 4 - hero.pointsInTalent(Talent.HOLY_INTUITION);
	}

	@Override
	public boolean canCast(Hero hero) {
		return super.canCast(hero) && hero.hasTalent(Talent.HOLY_INTUITION);
	}

	@Override
	protected void onItemSelected(HolyTome tome, Hero hero, Item item) {
		if (item == null){
			return;
		}

		item.cursedKnown = true;

		if (item.cursed){
			GLog.w(Messages.get(this, "cursed"));
		} else {
			GLog.i(Messages.get(this, "uncursed"));
		}

		hero.spend( 1f );
		hero.busy();
		hero.sprite.operate(hero.pos);
		hero.sprite.parent.add( new Identification( hero.sprite.center().offset( 0, -16 ) ) );

		Sample.INSTANCE.play( Assets.Sounds.READ );
		onSpellCast(tome, hero);

	}

}
