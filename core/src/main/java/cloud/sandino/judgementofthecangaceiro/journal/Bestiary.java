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

package cloud.sandino.judgementofthecangaceiro.journal;

import cloud.sandino.judgementofthecangaceiro.Badges;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.cleric.PowerOfMany;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.huntress.SpiritHawk;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.rogue.ShadowClone;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.rogue.SmokeBomb;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Acidic;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Albino;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.ArmoredBrute;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.ArmoredStatue;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Bandit;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Bat;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Bee;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Brute;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.CausticSlime;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Crab;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.CrystalGuardian;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.CrystalMimic;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.CrystalSpire;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.CrystalWisp;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DM100;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DM200;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DM201;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DM300;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DemonSpawner;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.DwarfKing;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.EbonyMimic;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Elemental;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Eye;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.FetidRat;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Ghoul;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Gnoll;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GnollExile;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GnollGeomancer;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GnollGuard;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GnollSapper;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GnollTrickster;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GoldenMimic;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Golem;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Goo;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.GreatCrab;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Guard;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.HermitCrab;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Mimic;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Monk;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Necromancer;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.PhantomPiranha;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Piranha;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Pylon;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Rat;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.RipperDemon;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.RotHeart;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.RotLasher;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Scorpio;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Senior;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Shaman;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Skeleton;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Slime;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Snake;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.SpectralNecromancer;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Spinner;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Statue;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Succubus;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Swarm;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Tengu;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Thief;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.TormentedSpirit;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Warlock;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.Wraith;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.YogDzewa;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.YogFist;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.Blacksmith;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.Ghost;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.Imp;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.MirrorImage;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.PrismaticImage;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.RatKing;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.Sheep;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.Shopkeeper;
import cloud.sandino.judgementofthecangaceiro.actors.mobs.npcs.Wandmaker;
import cloud.sandino.judgementofthecangaceiro.items.artifacts.DriedRose;
import cloud.sandino.judgementofthecangaceiro.items.quest.CorpseDust;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfLivingEarth;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfRegrowth;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfWarding;
import cloud.sandino.judgementofthecangaceiro.levels.rooms.special.SentryRoom;
import cloud.sandino.judgementofthecangaceiro.levels.traps.AlarmTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.BlazingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.BurningTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.ChillingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.ConfusionTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.CorrosionTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.CursingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.DisarmingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.DisintegrationTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.DistortionTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.ExplosiveTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.FlashingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.FlockTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.FrostTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GatewayTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GeyserTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GnollRockfallTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GrimTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GrippingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.GuardianTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.OozeTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.PitfallTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.PoisonDartTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.RockfallTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.ShockingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.StormTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.SummoningTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.TeleportationTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.TenguDartTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.ToxicTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.WarpingTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.WeakeningTrap;
import cloud.sandino.judgementofthecangaceiro.levels.traps.WornDartTrap;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import cloud.sandino.judgementofthecangaceiro.plants.BlandfruitBush;
import cloud.sandino.judgementofthecangaceiro.plants.Blindweed;
import cloud.sandino.judgementofthecangaceiro.plants.Earthroot;
import cloud.sandino.judgementofthecangaceiro.plants.Fadeleaf;
import cloud.sandino.judgementofthecangaceiro.plants.Firebloom;
import cloud.sandino.judgementofthecangaceiro.plants.Icecap;
import cloud.sandino.judgementofthecangaceiro.plants.Mageroyal;
import cloud.sandino.judgementofthecangaceiro.plants.Rotberry;
import cloud.sandino.judgementofthecangaceiro.plants.Sorrowmoss;
import cloud.sandino.judgementofthecangaceiro.plants.Starflower;
import cloud.sandino.judgementofthecangaceiro.plants.Stormvine;
import cloud.sandino.judgementofthecangaceiro.plants.Sungrass;
import cloud.sandino.judgementofthecangaceiro.plants.Swiftthistle;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

//contains all the game's various entities, mostly enemies, NPCS, and allies, but also traps and plants
public enum Bestiary {

	REGIONAL,
	BOSSES,
	UNIVERSAL,
	RARE,
	QUEST,
	NEUTRAL,
	ALLY,
	TRAP,
	PLANT;

	//tracks whether an entity has been encountered
	private final LinkedHashMap<Class<?>, Boolean> seen = new LinkedHashMap<>();
	//tracks enemy kills, trap activations, plant tramples, or just sets to 1 for seen on allies
	private final LinkedHashMap<Class<?>, Integer> encounterCount = new LinkedHashMap<>();

	//should only be used when initializing
	private void addEntities(Class<?>... classes ){
		for (Class<?> cls : classes){
			seen.put(cls, false);
			encounterCount.put(cls, 0);
		}
	}

	public Collection<Class<?>> entities(){
		return seen.keySet();
	}

	public String title(){
		return Messages.get(this, name() + ".title");
	}

	public int totalEntities(){
		return seen.size();
	}

	public int totalSeen(){
		int seenTotal = 0;
		for (boolean entitySeen : seen.values()){
			if (entitySeen) seenTotal++;
		}
		return seenTotal;
	}

	static {

		REGIONAL.addEntities(Rat.class, Snake.class, Gnoll.class, Swarm.class, Crab.class, Slime.class,
				Skeleton.class, Thief.class, DM100.class, Guard.class, Necromancer.class,
				Bat.class, Brute.class, Shaman.RedShaman.class, Shaman.BlueShaman.class, Shaman.PurpleShaman.class, Spinner.class, DM200.class,
				Ghoul.class, Elemental.FireElemental.class, Elemental.FrostElemental.class, Elemental.ShockElemental.class, Warlock.class, Monk.class, Golem.class,
				RipperDemon.class, DemonSpawner.class, Succubus.class, Eye.class, Scorpio.class);

		BOSSES.addEntities(Goo.class,
				Tengu.class,
				Pylon.class, DM300.class,
				DwarfKing.class,
				YogDzewa.Larva.class, YogFist.BurningFist.class, YogFist.SoiledFist.class, YogFist.RottingFist.class, YogFist.RustedFist.class,YogFist.BrightFist.class, YogFist.DarkFist.class, YogDzewa.class);

		UNIVERSAL.addEntities(Wraith.class, Piranha.class, Mimic.class, GoldenMimic.class, EbonyMimic.class, Statue.class, GuardianTrap.Guardian.class, SentryRoom.Sentry.class);

		RARE.addEntities(Albino.class, GnollExile.class, HermitCrab.class, CausticSlime.class,
				Bandit.class, SpectralNecromancer.class,
				ArmoredBrute.class, DM201.class,
				Elemental.ChaosElemental.class, Senior.class,
				Acidic.class,
				TormentedSpirit.class, PhantomPiranha.class, CrystalMimic.class, ArmoredStatue.class);

		QUEST.addEntities(FetidRat.class, GnollTrickster.class, GreatCrab.class,
				Elemental.NewbornFireElemental.class, RotLasher.class, RotHeart.class,
				CrystalWisp.class, CrystalGuardian.class, CrystalSpire.class, GnollGuard.class, GnollSapper.class, GnollGeomancer.class);

		NEUTRAL.addEntities(Ghost.class, RatKing.class, Shopkeeper.class, Wandmaker.class, Blacksmith.class, Imp.class, Sheep.class, Bee.class);

		ALLY.addEntities(MirrorImage.class, PrismaticImage.class,
				DriedRose.GhostHero.class,
				WandOfWarding.Ward.class, WandOfWarding.Ward.WardSentry.class, WandOfLivingEarth.EarthGuardian.class,
				ShadowClone.ShadowAlly.class, SmokeBomb.NinjaLog.class, SpiritHawk.HawkAlly.class, PowerOfMany.LightAlly.class);

		TRAP.addEntities(WornDartTrap.class, PoisonDartTrap.class, DisintegrationTrap.class, GatewayTrap.class,
				ChillingTrap.class, BurningTrap.class, ShockingTrap.class, AlarmTrap.class, GrippingTrap.class, TeleportationTrap.class, OozeTrap.class,
				FrostTrap.class, BlazingTrap.class, StormTrap.class, GuardianTrap.class, FlashingTrap.class, WarpingTrap.class,
				ConfusionTrap.class, ToxicTrap.class, CorrosionTrap.class,
				FlockTrap.class, SummoningTrap.class, WeakeningTrap.class, CursingTrap.class,
				GeyserTrap.class, ExplosiveTrap.class, RockfallTrap.class, PitfallTrap.class,
				DistortionTrap.class, DisarmingTrap.class, GrimTrap.class);

		PLANT.addEntities(Rotberry.class, Sungrass.class, Fadeleaf.class, Icecap.class,
				Firebloom.class, Sorrowmoss.class, Swiftthistle.class, Blindweed.class,
				Stormvine.class, Earthroot.class, Mageroyal.class, Starflower.class,
				BlandfruitBush.class,
				WandOfRegrowth.Dewcatcher.class, WandOfRegrowth.Seedpod.class, WandOfRegrowth.Lotus.class);

	}

	//some mobs and traps have different internal classes in some cases, so need to convert here
	private static final HashMap<Class<?>, Class<?>> classConversions = new HashMap<>();
	static {
		classConversions.put(CorpseDust.DustWraith.class,      Wraith.class);

		classConversions.put(Necromancer.NecroSkeleton.class,  Skeleton.class);

		classConversions.put(TenguDartTrap.class,              PoisonDartTrap.class);
		classConversions.put(GnollRockfallTrap.class,          RockfallTrap.class);

		classConversions.put(DwarfKing.DKGhoul.class,          Ghoul.class);
		classConversions.put(DwarfKing.DKWarlock.class,        Warlock.class);
		classConversions.put(DwarfKing.DKMonk.class,           Monk.class);
		classConversions.put(DwarfKing.DKGolem.class,          Golem.class);

		classConversions.put(YogDzewa.YogRipper.class,         RipperDemon.class);
		classConversions.put(YogDzewa.YogEye.class,            Eye.class);
		classConversions.put(YogDzewa.YogScorpio.class,        Scorpio.class);
	}

	public static boolean isSeen(Class<?> cls){
		for (Bestiary cat : values()) {
			if (cat.seen.containsKey(cls)) {
				return cat.seen.get(cls);
			}
		}
		return false;
	}

	public static void setSeen(Class<?> cls){
		if (classConversions.containsKey(cls)){
			cls = classConversions.get(cls);
		}
		for (Bestiary cat : values()) {
			if (cat.seen.containsKey(cls) && !cat.seen.get(cls)) {
				cat.seen.put(cls, true);
				Journal.saveNeeded = true;
			}
		}
		Badges.validateCatalogBadges();
	}

	public static int encounterCount(Class<?> cls) {
		for (Bestiary cat : values()) {
			if (cat.encounterCount.containsKey(cls)) {
				return cat.encounterCount.get(cls);
			}
		}
		return 0;
	}

	//used primarily when bosses are killed and need to clean up their minions
	public static boolean skipCountingEncounters = false;

	public static void countEncounter(Class<?> cls){
		countEncounters(cls, 1);
	}

	public static void countEncounters(Class<?> cls, int encounters){
		if (skipCountingEncounters){
			return;
		}
		if (classConversions.containsKey(cls)){
			cls = classConversions.get(cls);
		}
		for (Bestiary cat : values()) {
			if (cat.encounterCount.containsKey(cls) && cat.encounterCount.get(cls) != Integer.MAX_VALUE){
				cat.encounterCount.put(cls, cat.encounterCount.get(cls)+encounters);
				if (cat.encounterCount.get(cls) < -1_000_000_000){ //to catch cases of overflow
					cat.encounterCount.put(cls, Integer.MAX_VALUE);
				}
				Journal.saveNeeded = true;
			}
		}
	}

	private static final String BESTIARY_CLASSES    = "bestiary_classes";
	private static final String BESTIARY_SEEN       = "bestiary_seen";
	private static final String BESTIARY_ENCOUNTERS = "bestiary_encounters";

	public static void store( Bundle bundle ){

		ArrayList<Class<?>> classes = new ArrayList<>();
		ArrayList<Boolean> seen = new ArrayList<>();
		ArrayList<Integer> encounters = new ArrayList<>();

		for (Bestiary cat : values()) {
			for (Class<?> entity : cat.entities()) {
				if (cat.seen.get(entity) || cat.encounterCount.get(entity) > 0){
					classes.add(entity);
					seen.add(cat.seen.get(entity));
					encounters.add(cat.encounterCount.get(entity));
				}
			}
		}

		Class<?>[] storeCls = new Class[classes.size()];
		boolean[] storeSeen = new boolean[seen.size()];
		int[] storeEncounters = new int[encounters.size()];

		for (int i = 0; i < storeCls.length; i++){
			storeCls[i] = classes.get(i);
			storeSeen[i] = seen.get(i);
			storeEncounters[i] = encounters.get(i);
		}

		bundle.put( BESTIARY_CLASSES, storeCls );
		bundle.put( BESTIARY_SEEN, storeSeen );
		bundle.put( BESTIARY_ENCOUNTERS, storeEncounters );

	}

	public static void restore( Bundle bundle ){

		if (bundle.contains(BESTIARY_CLASSES)
				&& bundle.contains(BESTIARY_SEEN)
				&& bundle.contains(BESTIARY_ENCOUNTERS)){
			Class<?>[] classes = bundle.getClassArray(BESTIARY_CLASSES);
			boolean[] seen = bundle.getBooleanArray(BESTIARY_SEEN);
			int[] encounters = bundle.getIntArray(BESTIARY_ENCOUNTERS);

			for (int i = 0; i < classes.length; i++){
				for (Bestiary cat : values()){
					if (cat.seen.containsKey(classes[i])){
						cat.seen.put(classes[i], seen[i]);
						cat.encounterCount.put(classes[i], encounters[i]);
					}
				}
			}
		}

	}

}
