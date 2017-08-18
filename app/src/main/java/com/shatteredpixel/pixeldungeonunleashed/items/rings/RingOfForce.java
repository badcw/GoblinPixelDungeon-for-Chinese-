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

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;

public class RingOfForce extends Ring {

	{
		name = "武力戒指 ";
	}

	@Override
	protected RingBuff buff( ) {
		return new Force();
	}

	@Override
	public String desc() {
		if (isKnown()){
			String desc = "这个戒指增强了穿着者空手攻击的力量。 " +
					"这种额外的力量在挥舞武器时大部分是浪费的, " +
					"但是空手的攻击力会更强一些。 " +
					"一个负等级的戒指将会削弱穿戴者的打击。\n\n" +
					"在空手时的时候，";
			int str = Dungeon.hero.STR() - 8;
			desc += levelKnown ?
					"这个戒指的平均伤害是 " + (str/2+level + (int)(str*0.5f*level) + str*2)/2 + " 每次攻击造成":
					"这个戒指的典型平均伤害是" + (str/2+1 + (int)(str*0.5f) + str*2)/2 + " 每次攻击";
			desc += " 穿第二个武力戒指将会增强这一点。";
			return desc;
		} else
			return super.desc();
	}

	public class Force extends RingBuff {
	}
}

