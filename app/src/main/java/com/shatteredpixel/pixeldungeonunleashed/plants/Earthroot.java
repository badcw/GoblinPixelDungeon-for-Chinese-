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
package com.shatteredpixel.pixeldungeonunleashed.plants;

import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.watabou.noosa.Camera;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.EarthParticle;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.watabou.utils.Bundle;
import com.shatteredpixel.pixeldungeonunleashed.messages.Messages;

public class Earthroot extends Plant {

	private static final String TXT_DESC =
			"碰到地缚根后，它会固定住你" +
					"并在你的周围形成无法移动的天然护甲.";

	{
		image = 5;
		plantName = "地缚根";
	}

	@Override
	public void activate() {
		Char ch = Actor.findChar(pos);

		if (ch == Dungeon.hero) {
			Buff.affect( ch, Armor.class ).level = ch.HT;
		}

		if (Dungeon.visible[pos]) {
			CellEmitter.bottom( pos ).start( EarthParticle.FACTORY, 0.05f, 8 );
			Camera.main.shake( 1, 0.4f );
		}
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "地缚根";

			name = "之种" + plantName;
			image = ItemSpriteSheet.SEED_EARTHROOT;

			plantClass = Earthroot.class;
			alchemyClass = PotionOfParalyticGas.class;

			bones = true;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}

	public static class Armor extends Buff {

		private static final float STEP = 1f;

		private int pos;
		private int level;

		{
			type = buffType.POSITIVE;
		}

		@Override
		public boolean attachTo( Char target ) {
			pos = target.pos;
			return super.attachTo( target );
		}

		@Override
		public boolean act() {
			if (target.pos != pos) {
				detach();
			}
			spend( STEP );
			return true;
		}

		public int absorb( int damage ) {
			if (level <= damage-damage/2) {
				detach();
				return damage - level;
			} else {
				level -= damage-damage/2;
				return damage/2;
			}
		}

		public void level( int value ) {
			if (level < value) {
				level = value;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
		}

		@Override
		public String toString() {
			return Messages.get("植物护甲", level);
		}

		@Override
		public String desc() {
			return "一种自然的，不动的盔甲在保护你。" +
					"盔甲形成树皮和缠绕的绳子，缠绕在你的身体上。\n" +
					"\n" +
					" 这种奇特护甲会吸收你所遭受的所有物理伤害的50%，" +
					"直到它最终失去耐久性和消失。护甲也是不动的，" +
					"如果你试图移动，它会破碎并消失。\n" +
					"\n" +
					"这植物护甲还可以吸收 " + level + "，破损前可以吸收伤害。";
		}

		private static final String POS		= "pos";
		private static final String LEVEL	= "level";

		@Override
		public void storeInBundle( Bundle bundle ) {
			super.storeInBundle( bundle );
			bundle.put( POS, pos );
			bundle.put( LEVEL, level );
		}

		@Override
		public void restoreFromBundle( Bundle bundle ) {
			super.restoreFromBundle( bundle );
			pos = bundle.getInt( POS );
			level = bundle.getInt( LEVEL );
		}
	}
}
