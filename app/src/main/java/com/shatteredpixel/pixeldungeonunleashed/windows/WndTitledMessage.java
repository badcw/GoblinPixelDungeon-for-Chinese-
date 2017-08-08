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
package com.shatteredpixel.pixeldungeonunleashed.windows;

import com.shatteredpixel.pixeldungeonunleashed.GoblinsPixelDungeon;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;
import com.shatteredpixel.pixeldungeonunleashed.scenes.PixelScene;
import com.shatteredpixel.pixeldungeonunleashed.ui.Window;
import com.shatteredpixel.pixeldungeonunleashed.ui.RenderedTextMultiline;

public class WndTitledMessage extends Window {

	private static final int WIDTH_P    = 120;
	private static final int WIDTH_L    = 144;
	private static final int GAP	= 2;
	
	private BitmapTextMultiline normal;
	private BitmapTextMultiline highlighted;
	
	public WndTitledMessage( Image icon, String title, String message ) {
		
		this( new IconTitle( icon, title ), message );

	}
	
	public WndTitledMessage( Component titlebar, String message ) {
		
		super();

		int width = GoblinsPixelDungeon.landscape() ? WIDTH_L : WIDTH_P;

		titlebar.setRect( 0, 0, width, 0 );
		add( titlebar );
		
		RenderedTextMultiline text = PixelScene.renderMultiline( 6 );
		text.text( message, width );
		text.setPos( titlebar.left(), titlebar.bottom() + GAP );
		add( text );
		
		resize( width, (int)(normal.y + normal.height()) );
	}
}
