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
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.watabou.utils.Random;

public class Drowsy extends Buff {

	{
		type = buffType.NEUTRAL;
	}

	@Override
	public int icon() {
		return BuffIndicator.DROWSY;
	}

	public boolean attachTo( Char target ) {
		if (!target.immunities().contains(Sleep.class) && super.attachTo(target)) {
			if (cooldown() == 0)
				spend(Random.Int(3, 6));
			return true;
		}
		return false;
	}

	@Override
	public boolean act(){
			Buff.affect(target, MagicalSleep.class);

			detach();
			return true;
	}

	@Override
	public String toString() {
		return "催眠";
	}

	@Override
	public String desc() {
		return "这神奇的力量使人难以保持清醒。\n" +
				"\n" +
				"英雄如果受到伤害或完全健康则会抵抗睡意。\n" +
				"\n" +
				"在 " + dispTurns(cooldown()+1) + ",之后，目标将会进入深度魔法睡眠。";
	}
}
