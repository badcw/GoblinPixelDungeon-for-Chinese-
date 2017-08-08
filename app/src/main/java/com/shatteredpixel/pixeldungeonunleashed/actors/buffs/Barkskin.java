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

import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Barkskin extends Buff {

	private int level = 0;
	
	@Override
	public boolean act() {
		if (target.isAlive()) {

			spend( TICK );
			if (--level <= 0) {
				detach();
			}
			
		} else {
			
			detach();
			
		}
		
		return true;
	}
	
	public int level() {
		return level;
	}
	
	public void level( int value ) {
		if (level < value) {
			level = value;
		}
	}
	
	@Override
	public int icon() {
		return BuffIndicator.BARKSKIN;
	}
	
	@Override
	public String toString() {
		return "树肤";
	}

	@Override
	public String desc() {
		return "你的皮肤变硬了，摸起来像树皮一样粗糙而坚固。\n" +
				"\n" +
				"树肤增加了你的有效护甲，能让你更好地抵抗物理攻击。 " +
				"护甲提升数值每回合降低1点直到树肤效果消失。\n" +
				"\n" +
				"你的护甲当前增加到" + level +"。";
	}
}
