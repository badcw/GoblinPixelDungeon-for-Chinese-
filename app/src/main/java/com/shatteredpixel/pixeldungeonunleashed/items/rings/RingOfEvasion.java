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
package com.shatteredpixel.pixeldungeonunleashed.items.rings;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;

public class RingOfEvasion extends Ring {

	{
		name = "闪避戒指";
	}

	@Override
	protected RingBuff buff( ) {
		return new Evasion();
	}

	@Override
	public String desc() {
		return isKnown() ?
				"这枚戒指混淆了佩戴者的真实位置，使他们更难被发现和攻击。 " +
						"在敌人没有注意到你时这个戒指的功效会更强大" +
						"逃避将慢慢消失，未被发现的将恢复戒指的有效性。 " +
						"负等级的戒指会让佩戴者更容易被发现被攻击":
				super.desc();
	}

	//yup, the only ring in the game with logic inside of its class
	public class Evasion extends RingBuff {
		public int effectiveLevel;
		private int pos;

		@Override
		public boolean attachTo( Char target ) {

			pos = target.pos;
			effectiveLevel = Math.min(0, level);
			return super.attachTo(target);
		}

		@Override
		public boolean act() {

			boolean seen = false;

			for (Mob enemy : Dungeon.level.mobs.toArray(new Mob[0])){
				if (enemy.focusingHero()) {
					seen = true;
					break;
				}
			}

			if (level < 1){
				effectiveLevel = level;
			} else if (seen) {
				effectiveLevel = Math.max(effectiveLevel - 1, 0);
			} else {
				effectiveLevel = Math.min(effectiveLevel + 1, level);
			}

			return super.act();
		}
	}
}
