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

package cloud.sandino.judgementofthecangaceiro.actors.hero;

import cloud.sandino.judgementofthecangaceiro.Assets;
import cloud.sandino.judgementofthecangaceiro.Badges;
import cloud.sandino.judgementofthecangaceiro.Challenges;
import cloud.sandino.judgementofthecangaceiro.Dungeon;
import cloud.sandino.judgementofthecangaceiro.QuickSlot;
import cloud.sandino.judgementofthecangaceiro.SPDSettings;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.ArmorAbility;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.cleric.AscendedForm;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.cleric.PowerOfMany;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.cleric.Trinity;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.duelist.Challenge;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.duelist.ElementalStrike;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.duelist.Feint;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.huntress.NaturesPower;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.huntress.SpectralBlades;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.huntress.SpiritHawk;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.mage.ElementalBlast;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.mage.WarpBeacon;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.mage.WildMagic;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.rogue.DeathMark;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.rogue.ShadowClone;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.rogue.SmokeBomb;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.warrior.Endure;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.warrior.HeroicLeap;
import cloud.sandino.judgementofthecangaceiro.actors.hero.abilities.warrior.Shockwave;
import cloud.sandino.judgementofthecangaceiro.items.BrokenSeal;
import cloud.sandino.judgementofthecangaceiro.items.Item;
import cloud.sandino.judgementofthecangaceiro.items.Waterskin;
import cloud.sandino.judgementofthecangaceiro.items.armor.ClothArmor;
import cloud.sandino.judgementofthecangaceiro.items.artifacts.CloakOfShadows;
import cloud.sandino.judgementofthecangaceiro.items.artifacts.HolyTome;
import cloud.sandino.judgementofthecangaceiro.items.bags.VelvetPouch;
import cloud.sandino.judgementofthecangaceiro.items.food.Food;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfHealing;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfInvisibility;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfLiquidFlame;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfMindVision;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfPurity;
import cloud.sandino.judgementofthecangaceiro.items.potions.PotionOfStrength;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfIdentify;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfLullaby;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfMagicMapping;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfMirrorImage;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfRage;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfRemoveCurse;
import cloud.sandino.judgementofthecangaceiro.items.scrolls.ScrollOfUpgrade;
import cloud.sandino.judgementofthecangaceiro.items.wands.WandOfMagicMissile;
import cloud.sandino.judgementofthecangaceiro.items.weapon.SpiritBow;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.Cudgel;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.Dagger;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.Gloves;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.MagesStaff;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.Rapier;
import cloud.sandino.judgementofthecangaceiro.items.weapon.melee.WornShortsword;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.ThrowingKnife;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.ThrowingSpike;
import cloud.sandino.judgementofthecangaceiro.items.weapon.missiles.ThrowingStone;
import cloud.sandino.judgementofthecangaceiro.journal.Catalog;
import cloud.sandino.judgementofthecangaceiro.messages.Messages;
import com.watabou.utils.DeviceCompat;

public enum HeroClass {

	WARRIOR( HeroSubClass.BERSERKER, HeroSubClass.GLADIATOR ),
	MAGE( HeroSubClass.BATTLEMAGE, HeroSubClass.WARLOCK ),
	ROGUE( HeroSubClass.ASSASSIN, HeroSubClass.FREERUNNER ),
	HUNTRESS( HeroSubClass.SNIPER, HeroSubClass.WARDEN ),
	DUELIST( HeroSubClass.CHAMPION, HeroSubClass.MONK ),
	CLERIC( HeroSubClass.PRIEST, HeroSubClass.PALADIN );

	private HeroSubClass[] subClasses;

	HeroClass( HeroSubClass...subClasses ) {
		this.subClasses = subClasses;
	}

	public void initHero( Hero hero ) {

		hero.heroClass = this;
		Talent.initClassTalents(hero);

		Item i = new ClothArmor().identify();
		if (!Challenges.isItemBlocked(i)) hero.belongings.armor = (ClothArmor)i;

		i = new Food();
		if (!Challenges.isItemBlocked(i)) i.collect();

		new VelvetPouch().collect();
		Dungeon.LimitedDrops.VELVET_POUCH.drop();

		Waterskin waterskin = new Waterskin();
		waterskin.collect();

		new ScrollOfIdentify().identify();

		switch (this) {
			case WARRIOR:
				initWarrior( hero );
				break;

			case MAGE:
				initMage( hero );
				break;

			case ROGUE:
				initRogue( hero );
				break;

			case HUNTRESS:
				initHuntress( hero );
				break;

			case DUELIST:
				initDuelist( hero );
				break;

			case CLERIC:
				initCleric( hero );
				break;
		}

		if (SPDSettings.quickslotWaterskin()) {
			for (int s = 0; s < QuickSlot.SIZE; s++) {
				if (Dungeon.quickslot.getItem(s) == null) {
					Dungeon.quickslot.setSlot(s, waterskin);
					break;
				}
			}
		}

	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case WARRIOR:
				return Badges.Badge.MASTERY_WARRIOR;
			case MAGE:
				return Badges.Badge.MASTERY_MAGE;
			case ROGUE:
				return Badges.Badge.MASTERY_ROGUE;
			case HUNTRESS:
				return Badges.Badge.MASTERY_HUNTRESS;
			case DUELIST:
				return Badges.Badge.MASTERY_DUELIST;
			case CLERIC:
				return Badges.Badge.MASTERY_CLERIC;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		(hero.belongings.weapon = new WornShortsword()).identify();
		ThrowingStone stones = new ThrowingStone();
		stones.identify().collect();

		Dungeon.quickslot.setSlot(0, stones);

		if (hero.belongings.armor != null){
			hero.belongings.armor.affixSeal(new BrokenSeal());
			Catalog.setSeen(BrokenSeal.class); //as it's not added to the inventory
		}

		new PotionOfHealing().identify();
		new ScrollOfRage().identify();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff;

		staff = new MagesStaff(new WandOfMagicMissile());

		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().identify();
		new PotionOfLiquidFlame().identify();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.artifact = cloak).identify();
		hero.belongings.artifact.activate( hero );

		ThrowingKnife knives = new ThrowingKnife();
		knives.identify().collect();

		Dungeon.quickslot.setSlot(0, cloak);
		Dungeon.quickslot.setSlot(1, knives);

		new ScrollOfMagicMapping().identify();
		new PotionOfInvisibility().identify();
	}

	private static void initHuntress( Hero hero ) {

		(hero.belongings.weapon = new Gloves()).identify();
		SpiritBow bow = new SpiritBow();
		bow.identify().collect();

		Dungeon.quickslot.setSlot(0, bow);

		new PotionOfMindVision().identify();
		new ScrollOfLullaby().identify();
	}

	private static void initDuelist( Hero hero ) {

		(hero.belongings.weapon = new Rapier()).identify();
		hero.belongings.weapon.activate(hero);

		ThrowingSpike spikes = new ThrowingSpike();
		spikes.quantity(2).identify().collect(); //set quantity is 3, but Duelist starts with 2

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);
		Dungeon.quickslot.setSlot(1, spikes);

		new PotionOfStrength().identify();
		new ScrollOfMirrorImage().identify();
	}

	private static void initCleric( Hero hero ) {

		(hero.belongings.weapon = new Cudgel()).identify();
		hero.belongings.weapon.activate(hero);

		HolyTome tome = new HolyTome();
		(hero.belongings.artifact = tome).identify();
		hero.belongings.artifact.activate( hero );

		Dungeon.quickslot.setSlot(0, tome);

		new PotionOfPurity().identify();
		new ScrollOfRemoveCurse().identify();
	}

	public String title() {
		return Messages.get(HeroClass.class, name());
	}

	public String desc(){
		return Messages.get(HeroClass.class, name()+"_desc");
	}

	public String shortDesc(){
		return Messages.get(HeroClass.class, name()+"_desc_short");
	}

	public HeroSubClass[] subClasses() {
		return subClasses;
	}

	public ArmorAbility[] armorAbilities(){
		switch (this) {
			case WARRIOR: default:
				return new ArmorAbility[]{new HeroicLeap(), new Shockwave(), new Endure()};
			case MAGE:
				return new ArmorAbility[]{new ElementalBlast(), new WildMagic(), new WarpBeacon()};
			case ROGUE:
				return new ArmorAbility[]{new SmokeBomb(), new DeathMark(), new ShadowClone()};
			case HUNTRESS:
				return new ArmorAbility[]{new SpectralBlades(), new NaturesPower(), new SpiritHawk()};
			case DUELIST:
				return new ArmorAbility[]{new Challenge(), new ElementalStrike(), new Feint()};
			case CLERIC:
				return new ArmorAbility[]{new AscendedForm(), new Trinity(), new PowerOfMany()};
		}
	}

	public String spritesheet() {
		switch (this) {
			case WARRIOR: default:
				return Assets.Sprites.WARRIOR;
			case MAGE:
				return Assets.Sprites.MAGE;
			case ROGUE:
				return Assets.Sprites.ROGUE;
			case HUNTRESS:
				return Assets.Sprites.HUNTRESS;
			case DUELIST:
				return Assets.Sprites.DUELIST;
			case CLERIC:
				return Assets.Sprites.CLERIC;
		}
	}

	public String splashArt(){
		switch (this) {
			case WARRIOR: default:
				return Assets.Splashes.WARRIOR;
			case MAGE:
				return Assets.Splashes.MAGE;
			case ROGUE:
				return Assets.Splashes.ROGUE;
			case HUNTRESS:
				return Assets.Splashes.HUNTRESS;
			case DUELIST:
				return Assets.Splashes.DUELIST;
			case CLERIC:
				return Assets.Splashes.CLERIC;
		}
	}
	
	public boolean isUnlocked(){
		//always unlock on debug builds
		if (DeviceCompat.isDebug()) return true;

		switch (this){
			case WARRIOR: default:
				return true;
			case MAGE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_MAGE);
			case ROGUE:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_ROGUE);
			case HUNTRESS:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_HUNTRESS);
			case DUELIST:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_DUELIST);
			case CLERIC:
				return Badges.isUnlocked(Badges.Badge.UNLOCK_CLERIC);
		}
	}
	
	public String unlockMsg() {
		return shortDesc() + "\n\n" + Messages.get(HeroClass.class, name()+"_unlock");
	}

}
