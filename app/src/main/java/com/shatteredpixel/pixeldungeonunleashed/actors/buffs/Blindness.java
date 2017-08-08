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
package com.shatteredpixel.pixeldungeonunleashed.actors.buffs;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Blindness extends FlavourBuff {

	{
		type = buffType.NEGATIVE;
	}
	
	@Override
	public void detach() {
		super.detach();
		Dungeon.observe();
	}
	
	@Override
	public int icon() {
		return BuffIndicator.BLINDNESS;
	}
	
	@Override
	public String toString() {
		return "失明";
	}

	@Override
	public String desc() {
		return "失明使周围的环境进入了黑暗的阴霾。\n" +
				"\n" +
				"当你失明时,  你不能看到自己附近的地方 ，以至于使得远程" +
				"攻击变得毫无用处，并且失去远距离敌人的踪迹。 此外，失明 " +
				"时无法阅读卷轴和书籍。\n" +
				"\n" +
				"失明还将持续" + dispTurns() + "。";
	}
}
