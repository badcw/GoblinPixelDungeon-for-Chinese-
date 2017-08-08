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

import com.shatteredpixel.pixeldungeonunleashed.Badges;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;

public class Combo extends Buff {
	
	private static String TXT_COMBO = "%d 连击!";
	
	public int count = 0;
	
	@Override
	public int icon() {
		return BuffIndicator.COMBO;
	}
	
	@Override
	public String toString() {
		return "连击";
	}
	
	public int hit( Char enemy, int damage ) {
		
		count++;
		
		if (count >= 3) {
			
			Badges.validateMasteryCombo( count );
			
			GLog.p( TXT_COMBO, count );
			postpone( 1.41f - count / 10f );
			return (int)(damage * (count - 2) / 5f);
			
		} else {
			
			postpone( 1.1f );
			return 0;
			
		}
	}
	
	@Override
	public boolean act() {
		detach();
		return true;
	}

	@Override
	public String desc() {
		return "通过连击，角斗士将对敌人造成额外伤害。\n" +
				"\n" +
				"连击将会保持于连续攻击中，闪避则会消除这个效果。 " +
				"你的连击次数越高，攻击速度就越快。 " +
				"如果没能很快命中目标，就会重置连击。\n" +
				"\n" +
				(count <= 2 ? "你的连击还没有造成足够多的额外伤害。" :
				"连击给你 " + ((count - 2) * 20f) + " % 额外伤害。");
	}
}
