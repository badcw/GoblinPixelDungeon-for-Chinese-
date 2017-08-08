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

import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Amok extends FlavourBuff {

	{
		type = buffType.NEGATIVE;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.AMOK;
	}

	@Override
	public void detach() {
		super.detach();
		if (target instanceof Mob)
			((Mob)target).aggro( null );
	}

	@Override
	public String toString() {
		return "狂乱";
	}

	@Override
	public String desc() {
		return "狂乱会引起目标剧烈的愤怒和混乱。\n" +
				"\n" +
				"当一个生物狂乱时，他们会攻击任何靠近他们的人，不管是朋友还是敌人。\n" +
				"\n" +
				"狂乱将还持续 " + dispTurns() + ".";
	}
}
