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

public class RingOfTenacity extends Ring {

	{
		name = "韧性之戒";
	}

	@Override
	protected RingBuff buff( ) {
		return new Tenacity();
	}

	@Override
	public String desc() {
		return isKnown() ?
				"这枚戒指可以让佩戴者能够抵御原本致命的攻击 " +
						"佩戴者受伤越重，对伤害的抗性越高" +
						"负等级的戒指会让敌人更容易杀死佩戴者" :
				super.desc();
	}

	public class Tenacity extends RingBuff {
	}
}

