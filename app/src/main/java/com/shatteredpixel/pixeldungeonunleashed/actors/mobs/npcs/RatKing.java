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
package com.shatteredpixel.pixeldungeonunleashed.actors.mobs.npcs;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;

import com.shatteredpixel.pixeldungeonunleashed.sprites.RatKingSprite;

public class RatKing extends NPC {

	{
		name = "鼠王";
		spriteClass = RatKingSprite.class;
		
		state = SLEEPING;
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public float speed() {
		return 2f;
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public void interact() {
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (state == SLEEPING) {
			notice();
			yell( "我不睡觉！" );
			state = WANDERING;
		} else {
			yell( "你想干什么？我可没时间听这些废话。我的王国可不会自己运转下去！" );
		}
	}
	
	@Override
	public String description() {
		return ((RatKingSprite)sprite).festive ?
			"这只老鼠是比普通老鼠更大一点的有袋类老鼠。 " +
			"它戴着一顶小小的节日帽，而不是通常的皇冠。节日快乐！"
		:	"这只老鼠比普通老鼠大一点， " +
			"头上戴着小皇冠。";
	}
}
