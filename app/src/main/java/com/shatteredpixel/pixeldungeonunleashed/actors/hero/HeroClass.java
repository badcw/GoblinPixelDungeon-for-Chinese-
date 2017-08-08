/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
 *
 * Goblins Pixel Dungeon
 * Copyright (C) 2016 Mario Braun
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
package com.shatteredpixel.pixeldungeonunleashed.actors.hero;

import com.shatteredpixel.pixeldungeonunleashed.Assets;
import com.shatteredpixel.pixeldungeonunleashed.Badges;
import com.shatteredpixel.pixeldungeonunleashed.Challenges;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.GoblinsPixelDungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Fury;
import com.shatteredpixel.pixeldungeonunleashed.items.Egg;
import com.shatteredpixel.pixeldungeonunleashed.items.TomeOfMastery;
// import com.shatteredpixel.pixeldungeonunleashed.items.armor.ClothArmor;
import com.shatteredpixel.pixeldungeonunleashed.items.Torch;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.PlateArmor;
import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.CloakOfShadows;
import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.HummingTool;
import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.ShieldOfWonders;
import com.shatteredpixel.pixeldungeonunleashed.items.bags.AnkhChain;
import com.shatteredpixel.pixeldungeonunleashed.items.food.Food;
import com.shatteredpixel.pixeldungeonunleashed.items.keys.GoldenKey;
import com.shatteredpixel.pixeldungeonunleashed.items.keys.IronKey;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfExperience;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfMight;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfMindVision;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfToxicGas;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Glaive;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.MagesStaff;
import com.shatteredpixel.pixeldungeonunleashed.items.wands.WandOfMagicMissile;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Dagger;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.ShortSword;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Dart;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Boomerang;
import com.watabou.utils.Bundle;

public enum HeroClass {

	COMPLAINS( "战士" ), CHIEF( "法师" ), FUMBLES( "刺客" ), THACO( "女猎" );
	
	private String title;
	
	HeroClass( String title ) {
		this.title = title;
	}
	
	public static final String[] WAR_PERKS = {
		"战士初始拥有11点力量。",
		"... 开始拥有一把 +1短剑，这把短剑可以重铸用来升级其它武器。",
		"... 不擅长远程武器。",
		"食用食物可以回复一些生命值。",
		"战士不擅长魔法, 阅读卷轴可能会减少生命值。"
	};
	
	public static final String[] MAG_PERKS = {
		"法师初始拥有一根独特的法杖, 可以灌注其它法杖并获得其特性。",
		"法师可以将这根法杖作为近战武器使用。",
		"法师在使用法杖之后，会鉴定他们的部分属性。",
		"食用任何食物时都会使背包里的所有法杖回复1点充能。",
		"升级和鉴定卷轴在开局时就已鉴定。",
		"法杖充能速度更快。"
	};
	// switch go without food longer and thac0s more health from dew.
	public static final String[] ROG_PERKS = {
		"盗贼初始拥有一件独一无二的白痴胡子和一把+1魔法匕首。",
		"... 穿戴戒指会鉴定其类型。",
		"... 更适合穿轻甲。在装备力量需求低的护甲时，多余的力量需求会转化为闪避能力。",
		"... 更擅长发现隐藏门和陷阱。",
		"... 饮用露珠能恢复更多健康。",
		"魔法地图卷轴和毒气药剂在开局时就已鉴定。"
	};
	
	public static final String[] HUN_PERKS = {
		"女猎初始拥有18点生命值，并拥有一把独一无二的可升级的回旋镖。",
		"... 更擅长投掷武器。在使用力量需求低的投掷武器时，多余的力量需求会转化为额外伤害。",
		"... 可以从敌人身上回收一枚对其使用过的投掷武器。",
		"... 更耐饿。",
		"... 可以知道躲藏在障碍物后的敌人的位置。",
		"灵视药剂在开局时就已鉴定。",
		"怪物的物品掉落率变得更大。"
	};

	public void initHero( Hero hero ) {

		hero.heroClass = this;

		initCommon( hero );

		switch (this) {
			case COMPLAINS:
				initWarrior( hero );
				break;

			case CHIEF:
				initMage( hero );
				break;

			case FUMBLES:
				initRogue( hero );
				break;

			case THACO:
				initHuntress( hero );
				break;
		}

		if (Badges.isUnlocked( masteryBadge() )) {
			new TomeOfMastery().collect();
		}

		//hero.updateAwareness();
	}

	private static void initCommon( Hero hero ) {
//		if ((!Dungeon.isChallenged(Challenges.NO_ARMOR)) &&
//				((Dungeon.difficultyLevel <= Dungeon.DIFF_HARD) || (Dungeon.difficultyLevel == Dungeon.DIFF_ENDLESS)))
//			(hero.belongings.armor = new ClothArmor()).identify()
		// removed to show goblins in natural state a bit longer until player finds armor.

		if ((!Dungeon.isChallenged(Challenges.NO_FOOD)) &&
				((Dungeon.difficultyLevel <= Dungeon.DIFF_NORM) || (Dungeon.difficultyLevel == Dungeon.DIFF_ENDLESS)))
			new Food().identify().collect();

		if ((!Dungeon.isChallenged(Challenges.NO_FOOD)) && (Dungeon.difficultyLevel <= Dungeon.DIFF_EASY)) {
			for (int i = 0; i < 3; i++) {
				new Food().identify().collect();
				new ScrollOfIdentify().identify().collect();
			}
		}
		if (Dungeon.difficultyLevel == Dungeon.DIFF_TEST) {
			testHero(hero);
		}
	}

	public static void testHero(Hero hero) {
		hero.HT = 80;
		hero.HP = 80;
		new ShieldOfWonders().identify().collect();
		new Egg().collect();
		new AnkhChain().collect();
        new PlateArmor().identify().upgrade(10).collect();
        new Glaive().identify().upgrade(10).collect();
		new HummingTool().identify().collect();
        new GoldenKey().collect();
        new IronKey().collect();
		// things we only want a few of..
		for (int i = 0; i < 4; i++) {
			new PotionOfMight().collect();
			new ScrollOfRemoveCurse().collect();
		}
		for (int i = 0; i < 34; i++) {
			new PotionOfExperience().identify().collect();
			new ScrollOfMagicMapping().identify().collect();
		}
		// things we want a bunch of...
		for (int i = 0; i < 8; i++) {
			new Torch().identify().collect();
			new Food().collect();
			new ScrollOfIdentify().identify().collect();
			new ScrollOfUpgrade().collect();
		}
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
			case COMPLAINS:
				return Badges.Badge.MASTERY_WARRIOR;
			case CHIEF:
				return Badges.Badge.MASTERY_MAGE;
			case FUMBLES:
				return Badges.Badge.MASTERY_ROGUE;
			case THACO:
				return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initWarrior( Hero hero ) {
		hero.STR = hero.STR + 1;

		(hero.belongings.weapon = new ShortSword()).upgrade(1).identify();
		Dart darts = new Dart( 8 );
		darts.identify().collect();

		Dungeon.quickslot.setSlot(0, darts);

		//new PotionOfStrength().setKnown();
	}

	private static void initMage( Hero hero ) {
		MagesStaff staff = new MagesStaff(new WandOfMagicMissile());
		(hero.belongings.weapon = staff).identify();
		hero.belongings.weapon.activate(hero);

		Dungeon.quickslot.setSlot(0, staff);

		new ScrollOfUpgrade().setKnown();
		new ScrollOfIdentify().setKnown();
	}

	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new Dagger()).identify().upgrade(1);

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate( hero );

		Dart darts = new Dart( 8 );
		darts.identify().collect();

		Dungeon.quickslot.setSlot(0, cloak);
		if (GoblinsPixelDungeon.quickSlots() > 1)
			Dungeon.quickslot.setSlot(1, darts);

		new ScrollOfMagicMapping().setKnown();
		new PotionOfToxicGas().identify().collect();
	}

	private static void initHuntress( Hero hero ) {

		hero.HP = (hero.HT -= 2);

		(hero.belongings.weapon = new Dagger()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();

		Dungeon.quickslot.setSlot(0, boomerang);

		new PotionOfMindVision().setKnown();
	}
	
	public String title() {
		return title;
	}
	
	public String spritesheet() {
		
		switch (this) {
		case COMPLAINS:
            if (Dungeon.hero.buff(Fury.class) != null) {
                return Assets.BERZERK;
            }
            else {
                return Assets.WARRIOR;
            }
		case CHIEF:
			return Assets.MAGE;
		case FUMBLES:
			return Assets.ROGUE;
		case THACO:
			return Assets.HUNTRESS;
		}
		
		return null;
	}
	
	public String[] perks() {
		
		switch (this) {
		case COMPLAINS:
			return WAR_PERKS;
		case CHIEF:
			return MAG_PERKS;
		case FUMBLES:
			return ROG_PERKS;
		case THACO:
			return HUN_PERKS;
		}
		
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : FUMBLES;
	}
}
