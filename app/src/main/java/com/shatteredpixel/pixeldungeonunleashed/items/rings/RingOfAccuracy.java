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
package com.shatteredpixel.pixeldungeonunleashed.items.rings;

public class RingOfAccuracy extends Ring {

	{
		name = "精准戒指";
	}

	@Override
	protected RingBuff buff( ) {
		return new Accuracy();
	}

	@Override
	public String desc() {
		return isKnown() ?
				"这个戒指提高了你的专注力使敌人难以躲避你的攻击. "+
						"负等级的戒指会让你的攻击更容易躲避.":
				super.desc();
	}

	public class Accuracy extends RingBuff {
	}
}
