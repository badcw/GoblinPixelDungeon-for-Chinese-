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

import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;

public class MagicalSleep extends Buff {

	private static final float STEP = 1f;
	//public static final float SWS	= 1.5f;

	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target ) && !target.immunities().contains(Sleep.class)) {

			if (target instanceof Hero)
				if (target.HP == target.HT) {
					GLog.i("你太健康了，抵制了想要睡眠的冲动。");
					detach();
					return true;
				} else {
					GLog.i("你陷入了深深的魔法睡眠。");
				}
			else if (target instanceof Mob)
				((Mob)target).state = ((Mob)target).SLEEPING;

			target.paralysed = true;

			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean act(){
		if (target instanceof Hero) {
			target.HP = Math.min(target.HP+1, target.HT);
			((Hero) target).resting = true;
			if (target.HP == target.HT) {
				GLog.p("你醒来时感觉神清气爽，身体健康。");
				detach();
			}
		}
		spend( STEP );
		return true;
	}

	@Override
	public void detach() {
		target.paralysed = false;
		if (target instanceof Hero)
			((Hero) target).resting = false;
		super.detach();
	}

	@Override
	public int icon() {
		return BuffIndicator.MAGIC_SLEEP;
	}

	@Override
	public String toString() {
		return "魔法睡眠";
	}

	@Override
	public String desc() {
		return "这个生物进入了深深的魔法睡眠，他们不会自然醒来。\n" +
				"\n" +
				"魔法睡眠和普通睡眠很相似，除了只有受到伤害才会使目标醒来。 \n" +
				"\n" +
				"对于英雄来说，魔法睡眠具有恢复性，能让他们在睡眠中迅速康复。";
	}
}