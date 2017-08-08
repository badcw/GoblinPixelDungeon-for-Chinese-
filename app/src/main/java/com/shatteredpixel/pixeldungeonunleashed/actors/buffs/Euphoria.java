/*
 * Copyright (C) 2012-2015  Oleg Dolya
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
import com.shatteredpixel.pixeldungeonunleashed.items.rings.RingOfElements.Resistance;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Euphoria extends FlavourBuff {

	public static final float DURATION	= 8f;

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public int icon() {
		return BuffIndicator.EUPHORIA;
	}

	@Override
	public String toString() {
		return "欣快";
	}

	@Override
	public String desc() {
		return "当整个世界如此柔软时，想要行走成直线是很困难的！\n" +
				"\n" +
				"当生物获得欣快时，试图移动的生物会朝随机方向移动， " +
				"而不是他们想要移动到的位置。他们也会取下 \n" +
				"和扔掉东西，只是为了看它飞行的样子。\n" +
				"\n" +
				"欣快还将持续 " + dispTurns() + "。";
	}

	public static float duration( Char ch ) {
		Resistance r = ch.buff( Resistance.class );
		return r != null ? r.durationFactor() * DURATION : DURATION;
	}
}
