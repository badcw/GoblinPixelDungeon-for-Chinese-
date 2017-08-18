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
package com.shatteredpixel.pixeldungeonunleashed.scenes;

import com.watabou.noosa.Game;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndStory;

public class IntroScene extends PixelScene {

	private static final String TEXT =
			"许多英雄在你离开城市之前就进入了地牢. 一些 " +
					"带着财富和神器回来，大多数人再也没有回来." +
					"然而，没有一个人能走到深渊，找到Yendor的护身符. " +
					"据说它是被远古的罪恶守护的. " +
					"即便现在，也有邪恶的能量从下面泄露出来，进入城市." +
					"你感到你准备好迎接挑战.最重要的是, " +
					"你感到胜利女神在向你微笑. 现在是时候开始你的冒险了!";
	
	@Override
	public void create() {
		super.create();
		
		add( new WndStory( TEXT ) {
			@Override
			public void hide() {
				super.hide();
				Game.switchScene( InterlevelScene.class );
			}
		} );
		
		fadeIn();
	}
}
