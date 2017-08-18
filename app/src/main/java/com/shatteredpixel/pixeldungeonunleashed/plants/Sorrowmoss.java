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
package com.shatteredpixel.pixeldungeonunleashed.plants;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Poison;
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.PoisonParticle;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfToxicGas;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;

public class Sorrowmoss extends Plant {

	private static final String TXT_DESC =
			"断肠苔并不是一种苔藓，而是一种具有极强毒性，花瓣利如刀片.";

	{
		image = 2;
		plantName = "断肠苔";
	}

	@Override
	public void activate() {
		Char ch = Actor.findChar(pos);

		if (ch != null) {
			Buff.affect( ch, Poison.class ).set( Poison.durationFactor( ch ) * (4 + Dungeon.depth / 2) );
		}

		if (Dungeon.visible[pos]) {
			CellEmitter.center( pos ).burst( PoisonParticle.SPLASH, 3 );
		}
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "断肠苔";

			name = "之种 " + plantName;
			image = ItemSpriteSheet.SEED_SORROWMOSS;

			plantClass = Sorrowmoss.class;
			alchemyClass = PotionOfToxicGas.class;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}
