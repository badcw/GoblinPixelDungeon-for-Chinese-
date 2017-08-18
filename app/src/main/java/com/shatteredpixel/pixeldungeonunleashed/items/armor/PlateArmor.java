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
package com.shatteredpixel.pixeldungeonunleashed.items.armor;

import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;


public class PlateArmor extends Armor {

	{
		name = "板甲";
		image = ItemSpriteSheet.ARMOR_PLATE;
	}
	
	public PlateArmor() {
		super( 5 );
	}
	
	@Override
	public String desc() {
		return
				"厚重的金属板拼接到一起，" +
						"为能承受起其骇人重量的冒险者提供无与伦比的防御。";

	}
}
