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

public class RingOfFuror extends Ring {

	{
		name = "狂怒之戒";
	}

	@Override
	protected RingBuff buff( ) {
		return new Furor();
	}

	@Override
	public String desc() {
		return isKnown() ?
				"这枚戒指会激发佩戴者的怒火使其能够更快的攻击, " +
						"这种愤怒能让大力一击变快.因此慢速武器能够获得最多的收益 ," +
		"一个负等级的戒指会导致佩戴者攻击缓慢." :
		super.desc();
	}

	public class Furor extends RingBuff {
	}
}
