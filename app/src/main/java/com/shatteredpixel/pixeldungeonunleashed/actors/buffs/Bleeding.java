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
import com.shatteredpixel.pixeldungeonunleashed.ResultDescriptions;
import com.shatteredpixel.pixeldungeonunleashed.effects.Splash;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class Bleeding extends Buff {

	{
		type = buffType.NEGATIVE;
	}
	
	protected int level;
	
	private static final String LEVEL	= "level";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		bundle.put( LEVEL, level );
		
	}
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		level = bundle.getInt( LEVEL );
	}
	
	public void set( int level ) {
		this.level = level;
	}
	
	@Override
	public int icon() {
		return BuffIndicator.BLEEDING;
	}
	
	@Override
	public String toString() {
		return "流血";
	}
	
	@Override
	public boolean act() {
		if (target.isAlive()) {
			
			if ((level = Random.Int( level / 2, level )) > 0) {
				
				target.damage( level, this );
				if (target.sprite.visible) {
					Splash.at( target.sprite.center(), -PointF.PI / 2, PointF.PI / 6,
							target.sprite.blood(), Math.min( 10 * level / target.HT, 10 ) );
				}
				
				if (target == Dungeon.hero && !target.isAlive()) {
					Dungeon.fail( ResultDescriptions.BLEEDING );
					GLog.n( "你因流血过多而死..." );
				}
				
				spend( TICK );
			} else {
				detach();
			}
			
		} else {
			
			detach();
			
		}
		
		return true;
	}

	@Override
	public String desc() {
		return "那个伤口正令人不安地流出大量的血。\n" +
				"\n" +
				" 流血每个回合会引起伤害。每个回合的伤害减少一个随机数量，" +
				"直到最终停止流血。\n" +
				"\n" +
				"通常流血可以处理 ，" + level + " 为最大伤害。";
	}
}
