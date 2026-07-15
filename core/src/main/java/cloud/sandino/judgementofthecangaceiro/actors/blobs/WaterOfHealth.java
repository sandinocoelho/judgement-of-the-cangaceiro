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

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Buff;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Healing;
import cloud.sandino.judgementofthecangaceiro.actors.buffs.Hunger;
import cloud.sandino.judgementofthecangaceiro.actors.hero.Hero;
import cloud.sandino.judgementofthecangaceiro.effects.BlobEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.CellEmitter;
import cloud.sandino.judgementofthecangaceiro.effects.FloatingText;
import cloud.sandino.judgementofthecangaceiro.effects.Speck;
import cloud.sandino.judgementofthecangaceiro.effects.particles.ShadowParticle;
import cloud.sandino.judgementofthecangaceiro.effects.particles.ShaftParticle;
import cloud.sandino.judgementofthecangaceiro.items.Ankh;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.Waterskin;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfHealing;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfRemoveCurse;
import cloud.sandino.judgementofthecangaceiro.items.trinkets.VialOfBlood;
import cloud.sandino.judgementofthecangaceiro.journal.Notes.Landmark;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.sprites.CharSprite;
import cloud.sandino.judgementofthecangaceiro.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class WaterOfHealth extends WellWater {
	
	@Override
	protected boolean affectHero( Hero hero ) {
		
		if (!hero.isAlive()) return false;
		
		Sample.INSTANCE.play( Assets.Sounds.DRINK );

		PotionOfHealing.cure( hero );
		hero.belongings.uncurseEquipped();
		hero.buff( Hunger.class ).satisfy( Hunger.STARVING );

		if (VialOfBlood.delayBurstHealing()){
			Healing healing = Buff.affect(hero, Healing.class);
			healing.setHeal(hero.HT, 0, VialOfBlood.maxHealPerTurn());
			healing.applyVialEffect();
		} else {
			hero.HP = hero.HT;
			hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 4);
			hero.sprite.showStatusWithIcon(CharSprite.POSITIVE, Integer.toString(hero.HT), FloatingText.HEALING);
		}
		
		CellEmitter.get( hero.pos ).start( ShaftParticle.FACTORY, 0.2f, 3 );

		Dungeon.hero.interrupt();
	
		GLog.p( Messages.get(this, "procced") );
		
		return true;
	}
	
	@Override
	protected Item affectItem( Item item, int pos ) {
		if (item instanceof Waterskin && !((Waterskin)item).isFull()) {
			((Waterskin)item).fill();
			CellEmitter.get( pos ).start( Speck.factory( Speck.HEALING ), 0.4f, 4 );
			Sample.INSTANCE.play( Assets.Sounds.DRINK );
			return item;
		} else if ( item instanceof Ankh && !(((Ankh) item).isBlessed())){
			((Ankh) item).bless();
			CellEmitter.get( pos ).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
			Sample.INSTANCE.play( Assets.Sounds.DRINK );
			return item;
		} else if (ScrollOfRemoveCurse.uncursable(item)) {
			if (ScrollOfRemoveCurse.uncurse( null, item )){
				CellEmitter.get( pos ).start( ShadowParticle.UP, 0.05f, 10 );
			}
			Sample.INSTANCE.play( Assets.Sounds.DRINK );
			return item;
		}
		return null;
	}
	
	@Override
	public Landmark landmark() {
		return Landmark.WELL_OF_HEALTH;
	}
	
	@Override
	public void use( BlobEmitter emitter ) {
		super.use( emitter );
		emitter.start( Speck.factory( Speck.HEALING ), 0.5f, 0 );
	}
	
	@Override
	public String tileDesc() {
		return Messages.get(this, "desc");
	}
}
