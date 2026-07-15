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

package cloud.sandino.judgementofthecangaceiro.actors.buffs;

import cloud.sandino.judgementofthecangaceiro.actors.blobs.Blizzard;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.ConfusionGas;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.CorrosiveGas;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Electricity;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Fire;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Freezing;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Inferno;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.ParalyticGas;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Regrowth;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.SmokeScreen;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.StenchGas;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.StormCloud;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.ToxicGas;
import cloud.sandino.judgementofthecangaceiro.actors.blobs.Web;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Tengu;
import cloud.sandino.judgementofthecangaceiro.levels.rooms.special.MagicalFireRoom;
import cloud.sandino.judgementofthecangaceiro.ui.BuffIndicator;

public class BlobImmunity extends FlavourBuff {
	
	{
		type = buffType.POSITIVE;
	}
	
	public static final float DURATION	= 20f;
	
	@Override
	public int icon() {
		return BuffIndicator.IMMUNITY;
	}

	@Override
	public float iconFadePercent() {
		return Math.max(0, (DURATION - visualcooldown()) / DURATION);
	}

	{
		//all harmful blobs
		immunities.add( Blizzard.class );
		immunities.add( ConfusionGas.class );
		immunities.add( CorrosiveGas.class );
		immunities.add( Electricity.class );
		immunities.add( Fire.class );
		immunities.add( MagicalFireRoom.EternalFire.class );
		immunities.add( Freezing.class );
		immunities.add( Inferno.class );
		immunities.add( ParalyticGas.class );
		immunities.add( Regrowth.class );
		immunities.add( SmokeScreen.class );
		immunities.add( StenchGas.class );
		immunities.add( StormCloud.class );
		immunities.add( ToxicGas.class );
		immunities.add( Web.class );

		immunities.add(Tengu.FireAbility.FireBlob.class);
	}

}
