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
import com.shatteredpixel.pixeldungeonunleashed.items.rings.RingOfElements.Resistance;
import com.shatteredpixel.pixeldungeonunleashed.sprites.CharSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Paralysis extends FlavourBuff {

	private static final float DURATION	= 10f;

	{
		type = buffType.NEGATIVE;
	}
	
	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target )) {
			target.paralysed = true;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void detach() {
		super.detach();
		unfreeze(target);
	}
	
	@Override
	public int icon() {
		return BuffIndicator.PARALYSIS;
	}

	@Override
	public void fx(boolean on) {
		if (on) target.sprite.add(CharSprite.State.PARALYSED);
		else target.sprite.remove(CharSprite.State.PARALYSED);
	}

	@Override
	public String toString() {
		return "麻痹";
	}

	@Override
	public String desc() {
		return "通常来说，最糟糕的事情就是什么都不能做。\n" +
				"\n" +
				" 麻痹能完全停止目标的所有行动，迫使目标等到效果消失。" +
				"如果目标受到攻击，那么目标则会迅速摆脱麻痹效果。\n" +
				"\n" +
				"麻痹还将持续 " + dispTurns() + ",或者受到攻击。 \n";
	}

	public static float duration( Char ch ) {
		Resistance r = ch.buff( Resistance.class );
		return r != null ? r.durationFactor() * DURATION : DURATION;
	}

	public static void unfreeze( Char ch ) {
		if (ch.buff( Paralysis.class ) == null &&
				ch.buff( Frost.class ) == null) {

			ch.paralysed = false;
		}
	}
}
