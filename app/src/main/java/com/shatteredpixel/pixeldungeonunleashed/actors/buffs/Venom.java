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
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.watabou.utils.Bundle;

public class Venom extends Poison implements Hero.Doom {

	private int damage = 1;

	private static final String DAMAGE	= "damage";

	{
		type = buffType.NEGATIVE;
	}

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( DAMAGE, damage );

	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		damage = bundle.getInt( DAMAGE );
	}

	public void set(float duration, int damage) {
		set(duration);
		this.damage = damage;
	}

	@Override
	public int icon() {
		return BuffIndicator.POISON;
	}

	@Override
	public String toString() {
		return "剧毒";
	}

	@Override
	public String desc() {
		return "剧毒是一种极其致命和危险的效果。\n" +
				"\n" +
				"不像普通的中毒--随着时间的推移而伤害降低。剧毒作用在目标上的时间越长，伤害就越大。\n" +
				"\n" +
				"剧毒还将持续 " + dispTurns(left) + ",目前对你造成" + damage + " 伤害.";
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {
			target.damage(damage, this);
			if (damage < ((Dungeon.depth+1)/2)+1)
				damage++;

			//want it to act after the cloud of venom it came from.
			spend( TICK+0.1f );
			if ((left -= TICK) <= 0) {
				detach();
			}
		} else {
			detach();
		}

		return true;
	}

}
