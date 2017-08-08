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

import com.shatteredpixel.pixeldungeonunleashed.actors.blobs.ConfusionGas;
import com.shatteredpixel.pixeldungeonunleashed.actors.blobs.ParalyticGas;
import com.shatteredpixel.pixeldungeonunleashed.actors.blobs.StenchGas;
import com.shatteredpixel.pixeldungeonunleashed.actors.blobs.ToxicGas;
import com.shatteredpixel.pixeldungeonunleashed.actors.blobs.VenomGas;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class GasesImmunity extends FlavourBuff {
	
	public static final float DURATION	= 15f;
	
	@Override
	public int icon() {
		return BuffIndicator.IMMUNITY;
	}
	
	@Override
	public String toString() {
		return "免疫气体";
	}

	{
		immunities.add( ParalyticGas.class );
		immunities.add( ToxicGas.class );
		immunities.add( ConfusionGas.class );
		immunities.add( StenchGas.class );
		immunities.add( VenomGas.class );
	}

	@Override
	public String desc() {
		return "一种奇怪的力量正在过滤你周围的空气，它不会给你带来任何伤害，但它阻挡了 " +
				"除了空气以外的任何气体。这是如此有效，以至于闻不到任何气味！\n" +
				"\n" +
				"你免疫了所有气体对你带来的效果，直到这个效果消失为止。\n" +
				"\n" +
				"免疫气体还将持续 " + dispTurns() + "。";
	}
}
