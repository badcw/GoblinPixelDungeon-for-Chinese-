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
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.CloakOfShadows;
import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.TimekeepersHourglass;
import com.shatteredpixel.pixeldungeonunleashed.sprites.CharSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Invisibility extends FlavourBuff {

	public static final float DURATION	= 25f;

	{
		type = buffType.POSITIVE;
	}
	
	@Override
	public boolean attachTo( Char target ) {
		if (super.attachTo( target )) {
			if (target instanceof Mob) {
				((Mob) target).defenseSkill += 20;
			}
			target.invisible++;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void detach() {
		if (target.invisible > 0)
			target.invisible--;
		if (target instanceof Mob) {
			((Mob) target).defenseSkill -= 20;
		}
		super.detach();
	}
	
	@Override
	public int icon() {
		return BuffIndicator.INVISIBLE;
	}

	@Override
	public void fx(boolean on) {
		if (on) target.sprite.add( CharSprite.State.INVISIBLE );
		else if (target.invisible == 0) target.sprite.remove( CharSprite.State.INVISIBLE );
	}

	@Override
	public String toString() {
		return "隐形";
	}

	@Override
	public String desc() {
		return "你已经完全地融入了周围的地形，使你不可能被其它生物看到。\n" +
				"\n" +
				"当你隐形时，敌人无法攻击或跟随你。" +
				"物理攻击和使用魔法（如卷轴和法杖）会使你立即退出隐形状态。\n" +
				"\n" +
				"隐形还将持续" + dispTurns() + "。";
	}

	public static void dispel() {
		Invisibility buff = Dungeon.hero.buff( Invisibility.class );
		if (buff != null) {
			buff.detach();
		}
		CloakOfShadows.cloakStealth cloakBuff = Dungeon.hero.buff( CloakOfShadows.cloakStealth.class );
		if (cloakBuff != null) {
			cloakBuff.act();
			cloakBuff.detach();
		}
		//this isn't a form of invisibilty, but it is meant to dispel at the same time as it.
		TimekeepersHourglass.timeFreeze timeFreeze = Dungeon.hero.buff( TimekeepersHourglass.timeFreeze.class );
		if (timeFreeze != null) {
			timeFreeze.detach();
		}
	}
}
