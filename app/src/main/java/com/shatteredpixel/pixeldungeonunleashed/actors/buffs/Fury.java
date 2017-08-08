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
import com.shatteredpixel.pixeldungeonunleashed.sprites.HeroSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.ui.StatusPane;

public class Fury extends Buff {
	
	public static float LEVEL	= 0.5f;

	{
		type = buffType.POSITIVE;
	}
	
	@Override
	public boolean act() {
		if (target.HP > target.HT * LEVEL) {
			detach();
			((HeroSprite)Dungeon.hero.sprite).refresh();
            StatusPane.refreshavatar();
		}
		
		spend( TICK );
		
		return true;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.FURY;
	}
	
	@Override
	public String toString() {
		return "狂怒";
	}

	@Override
	public String desc() {
		return "你生气了，你生气的时候敌人是不会喜欢你的。\n" +
				"\n" +
				"狂怒会使你体内燃烧，增加50%物理攻击伤害。 \n" +
				"\n" +
				"只要你生命值在40%以下，这种狂怒效果就会一直持续下去。\n";
	}
}
