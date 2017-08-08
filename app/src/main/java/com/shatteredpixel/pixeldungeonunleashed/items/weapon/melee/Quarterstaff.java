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
package com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee;

import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;

public class Quarterstaff extends MeleeWeapon {

	{
		name = "quarterstaff";
		image = ItemSpriteSheet.QUARTERSTAFF;
	}
	
	public Quarterstaff() {
		super( 2, 1f, 1f );
	}
	
	@Override
	public String desc() {
		return "A staff of hardwood, its ends are shod with iron.";
	}
}
