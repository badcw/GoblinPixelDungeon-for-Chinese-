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

public class LockedFloor extends Buff {
	//this buff is purely meant as a visual indicator that the gameplay implications of a level seal are in effect.

	@Override
	public boolean act() {
		spend(TICK);

		if (!Dungeon.level.locked)
			detach();

		return true;
	}

	@Override
	public int icon() {
		return BuffIndicator.LOCKED_FLOOR;
	}

	@Override
	public String toString() {
		return "背水一战";
	}

	@Override
	public String desc() {
		return "现在楼层被锁上了，你不能离开它！\n" +
				"\n" +
				"当你背水一战时，你不会变得更饿，也不会受到由饥饿引起的伤害，" +
				"但是你目前的饥饿状态仍然有效。例如，如果你饿了，你不会受到由饥饿引起的" +
				"伤害，也仍然不会恢复健康。\n" +
				"\n" +
				"另外，如果你使用未祝福的十字架复活时处于背水一战的话，那么该楼层将重置。\n" +
				"\n" +
				"只有击杀这层的boss才能解除背水一战。\n";
	}
}
