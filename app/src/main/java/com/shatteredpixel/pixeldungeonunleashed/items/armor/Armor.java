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
package com.shatteredpixel.pixeldungeonunleashed.items.armor;

import java.util.ArrayList;

import com.shatteredpixel.pixeldungeonunleashed.Badges;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.ResultDescriptions;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.items.EquipableItem;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.glyphs.*;
import com.shatteredpixel.pixeldungeonunleashed.sprites.HeroSprite;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSprite;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.shatteredpixel.pixeldungeonunleashed.messages.Messages;

public class Armor extends EquipableItem {

	private static final int HITS_TO_KNOW    = 10;

	private static final String TXT_EQUIP_CURSED	= "你的%s 勒得你生疼。";
		
	private static final String TXT_IDENTIFY	= "你现在足够熟悉你的%s了. 它是%s。";
	
	private static final String TXT_TO_STRING	= "%s :%d";
	
	private static final String TXT_INCOMPATIBLE =
		"不同类型的魔法冲突消除了护甲上的刻印!";
	
	public int tier;
	
	public int STR;
	public int DR;
	
	private int hitsToKnow = HITS_TO_KNOW;
	
	public Glyph glyph;
	
	public Armor( int tier ) {
		
		this.tier = tier;
		levelCap = tier + 3 + (tier / 2);
		STR = typicalSTR();
		DR = typicalDR();
	}

	private static final String UNFAMILIRIARITY	= "未鉴定";
	private static final String GLYPH			= "刻印";
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( UNFAMILIRIARITY, hitsToKnow );
		bundle.put( GLYPH, glyph );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		if ((hitsToKnow = bundle.getInt( UNFAMILIRIARITY )) == 0) {
			hitsToKnow = HITS_TO_KNOW;
		}
		inscribe((Glyph) bundle.get(GLYPH));
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add(isEquipped(hero) ? AC_UNEQUIP : AC_EQUIP);
		return actions;
	}
	
	@Override
	public boolean doEquip( Hero hero ) {
		
		detach(hero.belongings.backpack);

		if (hero.belongings.armor == null || hero.belongings.armor.doUnequip( hero, true, false )) {
			
			hero.belongings.armor = this;
			
			cursedKnown = true;
			if (cursed) {
				equipCursed( hero );
				GLog.n( TXT_EQUIP_CURSED, toString() );
			}
			
			((HeroSprite)hero.sprite).updateArmor();

			hero.spendAndNext( 2 * time2equip( hero ) );
			return true;
			
		} else {
			
			collect( hero.belongings.backpack );
			return false;
			
		}
	}

	@Override
	protected float time2equip( Hero hero ) {
		return hero.speed();
	}

	@Override
	public boolean doUnequip( Hero hero, boolean collect, boolean single ) {
		if (super.doUnequip( hero, collect, single )) {

			hero.belongings.armor = null;
			((HeroSprite)hero.sprite).updateArmor();

			return true;

		} else {

			return false;

		}
	}
	
	@Override
	public boolean isEquipped( Hero hero ) {
		return hero.belongings.armor == this;
	}
	
    @Override
	public Item upgrade( int n ) {
		cursed = false;
		cursedKnown = true;
		this.level = n;

		for (int i = 0; i < n; i++) {
			DR += tier;
			STR--;
		}
		updateQuickslot();

		return this;
	}

	@Override
	public Item upgrade() {
		return upgrade( false );
	}
	
	public Item upgrade( boolean inscribe ) {
		if (glyph != null) {
			if (!inscribe && Random.Int( level ) > 0) {
				GLog.w( TXT_INCOMPATIBLE );
				inscribe( null );
			}
		} else {
			if (inscribe) {
				inscribe( Glyph.random() );
			}
		}

		if (upgradeSucceds()) {
			DR += tier;
			STR--;
			super.upgrade();
		} else {
			GLog.n("魔法无法附上你的护甲。");
		}

		return this;
	}
	
	@Override
	public Item degrade() {
		DR -= tier;
		STR++;
		
		return super.degrade();
	}
	
	public int proc( Char attacker, Char defender, int damage ) {
		
		if (glyph != null) {
			damage = glyph.proc( this, attacker, defender, damage );
		}
		
		if (!levelKnown) {
			if (--hitsToKnow <= 0) {
				levelKnown = true;
				GLog.w( TXT_IDENTIFY, name(), toString() );
				Badges.validateItemLevelAquired( this );
			}
		}
		
		return damage;
	}
	
	@Override
	public String toString() {
		return levelKnown ? Messages.format( TXT_TO_STRING, super.toString(), STR ) : super.toString();
	}
	
	@Override
	public String name() {
		return glyph == null ? super.name() : glyph.name( super.name() );
	}
	
	@Override
	public String info() {
		String name = name();
		StringBuilder info = new StringBuilder( desc() );
		
		if (levelKnown) {
			info.append(
				"\n\n这件 " + name + " 最多可以提供 " +
				Math.max( DR, 0 ) + " 点伤害减免。 " );
			
			if (STR > Dungeon.hero.STR()) {

				if (isEquipped( Dungeon.hero )) {
					info.append(
							"\n\n由于你的力量不足 " +
									"移动速度和防御都会被降低. " );
				} else {
					info.append(
							"\n\n由于你的力量不足这件护甲 " +
									"会减少你的移速和防御. " );
				}

			}
		} else {
			info.append(
					"\n\n这件 " + name + " 最多可以提供 " + typicalDR() + " 点伤害减免 " +
							"并且需要 " + typicalSTR() + " 点力量. " );
			if (typicalSTR() > Dungeon.hero.STR()) {
				info.append( "或许这件护甲对你来说太重了. " );
			}
		}

		if (glyph != null) {
			info.append( "它已被附魔; ");
			info.append( glyph.glyphDescription() );
		}

		if (isEquipped( Dungeon.hero )) {
			info.append( "\n\n你穿着 " + name +
					(cursed? ", 因为它被诅咒了, 你无法脱下." : ".") );
		} else {
			if (cursedKnown && cursed) {
				info.append( "\n\n你能感受到一股潜伏在 " + name + "里的恶毒魔法." );
			}
		}


		return info.toString();
	}
	
	@Override
	public Item random() {
		int upgrade_odds;
		switch (Dungeon.difficultyLevel) {
			case Dungeon.DIFF_TUTOR:
			case Dungeon.DIFF_EASY:
				upgrade_odds = 4;
				break;
			case Dungeon.DIFF_NORM:
				upgrade_odds = 3;
				break;
			case Dungeon.DIFF_HARD:
				upgrade_odds = 2;
				break;
			case Dungeon.DIFF_NTMARE:
				upgrade_odds = 2;
				break;
			default:
				upgrade_odds = 3;
				break;
		}

		if (Random.Float() < 0.4) {
			int n = 1;
			if (Random.Int( 3 ) == 0) {
				n++;
				if (Random.Int( 5 ) == 0) {
					n++;
				}
			}
			if (Random.Int( 6 ) <= upgrade_odds) {
				upgrade( n );
			} else {
				degrade( n );
				cursed = true;
			}
		}
		
		if (Random.Int( 10 ) == 0) {
			inscribe();
		}
		
		return this;
	}
	
	public int typicalSTR() {
		return 7 + tier * 2;
	}
	
	public int typicalDR() {
		return tier * 2;
	}
	
	@Override
	public int price() {
		int price = 10 * (1 << (tier - 1));
		if (glyph != null) {
			price *= 1.5;
		}
		if (cursed && cursedKnown) {
			price /= 2;
		}
		if (levelKnown) {
			if (level > 0) {
				price *= (level + 1);
			} else if (level < 0) {
				price /= (1 - level);
			}
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

	public Armor inscribe( Glyph glyph ) {

		if (glyph != null && this.glyph == null) {
			DR += tier;
		} else if (glyph == null && this.glyph != null) {
			DR -= tier;
		}

		this.glyph = glyph;

		return this;
	}

	public Armor inscribe() {

		Class<? extends Glyph> oldGlyphClass = glyph != null ? glyph.getClass() : null;
		Glyph gl = Glyph.random();
		while (gl.getClass() == oldGlyphClass) {
			gl = Armor.Glyph.random();
		}

		return inscribe( gl );
	}

	public boolean isInscribed() {
		return glyph != null;
	}
	
	@Override
	public ItemSprite.Glowing glowing() {
		return glyph != null ? glyph.glowing() : null;
	}
	
	public static abstract class Glyph implements Bundlable {
		
		private static final Class<?>[] glyphs = new Class<?>[]{
			Bounce.class, Affection.class, AntiEntropy.class, Multiplicity.class,
			Potential.class, Metabolism.class, Stench.class, Viscosity.class,
			Displacement.class, Entanglement.class, Resistance.class };
		
		private static final float[] chances= new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
			
		public abstract int proc( Armor armor, Char attacker, Char defender, int damage );

		public String glyphDescription() { return ""; }

		public String name() {
			return name( "glyph" );
		}
		
		public String name( String armorName ) {
			return armorName;
		}
		
		@Override
		public void restoreFromBundle( Bundle bundle ) {
		}

		@Override
		public void storeInBundle( Bundle bundle ) {
		}
		
		public ItemSprite.Glowing glowing() {
			return ItemSprite.Glowing.WHITE;
		}
		
		public boolean checkOwner( Char owner ) {
			if (!owner.isAlive() && owner instanceof Hero) {

				Dungeon.fail( Messages.format( ResultDescriptions.GLYPH, name() ) );
				GLog.n( "%s杀死了你...", name() );

				Badges.validateDeathFromGlyph();
				return true;
				
			} else {
				return false;
			}
		}
		
		@SuppressWarnings("unchecked")
		public static Glyph random() {
			try {
				return ((Class<Glyph>)glyphs[ Random.chances( chances ) ]).newInstance();
			} catch (Exception e) {
				return null;
			}
		}
		
	}
}
