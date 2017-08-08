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
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.EarthParticle;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class EarthImbue extends FlavourBuff {

	public static final float DURATION	= 30f;

	public void proc(Char enemy){
		Buff.affect(enemy, Roots.class, 2);
		CellEmitter.bottom(enemy.pos).start(EarthParticle.FACTORY, 0.05f, 8);
	}

	@Override
	public int icon() {
		return BuffIndicator.ROOTS;
	}

	@Override
	public String toString() {
		return "大地之力";
	}

	@Override
	public String desc() {
		return "你获得了大地的力量！\n" +
				"\n" +
				"当你拥有这个效果时，你造成的所有物理攻击将会使敌人束缚在原来的位置上。\n" +
				"\n" +
				"大地之力还将持续 " + dispTurns() + "。";
	}

	{
		immunities.add( Paralysis.class );
		immunities.add( Roots.class );
		immunities.add( Slow.class );
	}
}