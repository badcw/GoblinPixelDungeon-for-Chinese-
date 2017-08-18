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

public class RingOfWealth extends Ring {

	{
		name = "财富之戒";
	}

	@Override
	protected RingBuff buff( ) {
		return new Wealth();
	}

	@Override
	public String desc() {
		return isKnown() ?
				"说不清楚这个戒指如何起效，好运会以任何形式 " +
						"微妙的影响这冒险家的一生 " +
						"自然，一个负等级的戒指，会为佩戴者带来厄运" :
				super.desc();
	}

	public class Wealth extends RingBuff {
	}
}
