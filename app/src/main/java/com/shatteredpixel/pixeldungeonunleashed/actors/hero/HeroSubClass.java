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
package com.shatteredpixel.pixeldungeonunleashed.actors.hero;

import com.watabou.utils.Bundle;

public enum HeroSubClass {

	NONE( null, null ),
	
	GLADIATOR( "角斗士",
		"_角斗士_使用近战武器进行成功攻击能获得连击效果, " +
		"每一次连续的成功攻击都会造成更大的伤害。" ),
	BERSERKER( "狂战士",
		"当_狂战士_严重受伤时，会获得狂怒效果，能显著增加" +
		"其近战伤害输出。" ),
	
	WARLOCK( "术士",
		" _术士_能从食物中获得额外的法杖充能补给，但不能满足他的饥饿感。" +
		"因此，用任何方式杀死一个敌人后，他都能洗净自己的灵魂并使他的伤口愈合及充饥。" ),
	BATTLEMAGE( "战斗法师",
		"当使用魔杖近战时, _战斗法师_ 会获得取绝于吸收法杖类型 " +
		"的额外效果。他的魔杖也会在战斗中回复充能。" ),
	
	ASSASSIN( "刺客",
		"当_刺客_对敌人进行突然袭击时，目标会受到额外的伤害" ),
	FREERUNNER( "疾行者",
		"_疾行者_移动速度更快，负担更小且更耐饿。" +
		"如果你隐形，移动速度还将会提升得更高。" ),
		
	SNIPER( "阻击手",
		"_阻击手_ 能够看出敌人装甲上的的弱点， " +
		"并能有效地使用投掷武器而不浪费。" ),
	WARDEN( "守望者",
		"守望者与自然的联系更加密切。能够从植物中获得更多的露珠 " +
		"和种子。并且践踏高草会还获得一个临时的树肤buff。" );
	
	private String title;
	private String desc;
	
	HeroSubClass( String title, String desc ) {
		this.title = title;
		this.desc = desc;
	}
	
	public String title() {
		return title;
	}
	
	public String desc() {
		return desc;
	}
	
	private static final String SUBCLASS	= "subClass";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( SUBCLASS, toString() );
	}
	
	public static HeroSubClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( SUBCLASS );
		try {
			return valueOf( value );
		} catch (Exception e) {
			return NONE;
		}
	}
	
}
