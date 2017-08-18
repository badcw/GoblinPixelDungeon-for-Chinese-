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

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.items.food.Yumyuck;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;
public class YumyuckMoss extends Plant {

	private static final String TXT_DESC =
			"因为它是世界上最美味和最令人厌恶的食物而闻名。 " +
					"最近，由于化学物的使用，美味藓已经被收获了许多了.";

	{
		image = 8;
		plantName = "美味藓";
	}

	@Override
	public void activate() {
		Char ch = Actor.findChar(pos);

		Dungeon.level.drop( new Yumyuck(), pos ).sprite.drop();
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "美味藓";

			name = "孢子 " + plantName;
			image = ItemSpriteSheet.SEED_BLANDFRUIT;

			plantClass = YumyuckMoss.class;
			alchemyClass = null;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}

