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

import java.util.HashMap;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.HeroClass;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Shuriken;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;
import com.shatteredpixel.pixeldungeonunleashed.sprites.MissileSprite;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.watabou.utils.Callback;

public class HuntressArmor extends ClassArmor {

	private static final String TXT_NO_ENEMIES 		= "视野里没有敌人!";
	private static final String TXT_NOT_HUNTRESS	= "只有 女猎 能使用这件护甲!";

	private static final String AC_SPECIAL = "回旋踢";
	
	{
		name = "野蛮之触";
		image = ItemSpriteSheet.ARMOR_HUNTRESS;
	}
	
	private HashMap<Callback, Mob> targets = new HashMap<Callback, Mob>();
	
	@Override
	public String special() {
		return AC_SPECIAL;
	}
	
	@Override
	public void doSpecial() {
		
		Item proto = new Shuriken();
		
		for (Mob mob : Dungeon.level.mobs) {
			if (Level.distance(curUser.pos, mob.pos) <= 2) {
				
				Callback callback = new Callback() {
					@Override
					public void call() {
						curUser.attack( targets.get( this ) );
						targets.remove( this );
						if (targets.isEmpty()) {
							curUser.spendAndNext( curUser.attackDelay() );
						}
					}
				};
				
				((MissileSprite)curUser.sprite.parent.recycle( MissileSprite.class )).
					reset( curUser.pos, mob.pos, proto, callback );
				
				targets.put( callback, mob );
			}
		}
		
		if (targets.size() == 0) {
			GLog.w( TXT_NO_ENEMIES );
			return;
		}
		
		curUser.HP -= (curUser.HP / 3);
		
		curUser.sprite.zap( curUser.pos );
		curUser.busy();
	}
	
	@Override
	public boolean doEquip( Hero hero ) {
		if (hero.heroClass == HeroClass.THACO) {
			return super.doEquip( hero );
		} else {
			GLog.w( TXT_NOT_HUNTRESS );
			return false;
		}
	}
	
	@Override
	public String desc() {
		return
				"这件护甲需要哥布林的野蛮之触。这给予了移动上 " +
						"的高度自由，使得能踢中周围所有的敌人， 造成的伤害 " +
						"取决于他装备的武器。";
	}
}