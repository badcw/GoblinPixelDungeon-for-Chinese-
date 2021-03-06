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
import com.shatteredpixel.pixeldungeonunleashed.items.rings.RingOfElements.Resistance;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Weakness extends FlavourBuff {

	public static final float DURATION = 40f;

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public int icon() {
		return BuffIndicator.WEAKNESS;
	}
	
	@Override
	public String toString() {
		return "虚弱";
	}
	
	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target )) {
			Hero hero = (Hero)target;
			hero.weakened = true;
			hero.belongings.discharge();
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void detach() {
		super.detach();
		((Hero)target).weakened = false;
	}
	
	public static float duration( Char ch ) {
		Resistance r = ch.buff( Resistance.class );
		return r != null ? r.durationFactor() * DURATION : DURATION;
	}

	@Override
	public String desc() {
		return "你的装备突然变得很重。\n" +
				"\n" +
				"虚弱魔法正在影响着你，使你的力量值减少2点。\n" +
				"\n" +
				"虚弱还将持续 " + dispTurns() + "。";
	}
}
