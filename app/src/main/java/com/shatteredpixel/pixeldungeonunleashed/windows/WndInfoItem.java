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

import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.Artifact;
import com.shatteredpixel.pixeldungeonunleashed.items.rings.Ring;
import com.shatteredpixel.pixeldungeonunleashed.items.wands.Wand;
import com.watabou.noosa.BitmapTextMultiline;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap.Type;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.scenes.PixelScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.ItemSlot;
import com.shatteredpixel.pixeldungeonunleashed.ui.Window;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.shatteredpixel.pixeldungeonunleashed.ui.RenderedTextMultiline;
import com.shatteredpixel.pixeldungeonunleashed.messages.Messages;

public class WndInfoItem extends Window {

	private static final String TTL_CHEST           = "宝箱";
	private static final String TTL_LOCKED_CHEST	= "上锁的宝箱";
	private static final String TTL_CRYSTAL_CHEST	= "水晶宝箱";
	private static final String TTL_TOMB			= "墓碑";
	private static final String TTL_SKELETON		= "残骸";
	private static final String TTL_REMAINS 		= "英雄残骸";
	private static final String TXT_WONT_KNOW		= "在你打开之前,你不知道里面有什么";
	private static final String TXT_NEED_KEY		= TXT_WONT_KNOW + " 要打开它你需要一把钥匙.";
	private static final String TXT_INSIDE			= "你能看见 %s 在里面, 但是你需要一把金钥匙来打开它";
	private static final String TXT_OWNER	=
			"这个古老的坟墓可能会有有用的东西, " +
					"但是它的主人肯定会反对你的搜查.";
	private static final String TXT_SKELETON =
			"这是一些不幸的冒险家留下的全部东西" +
					"或许它值得找一找看有没有珍贵的东西.";
	private static final String TXT_REMAINS =
			"这是你的上一位冒险者留下的全部东西. " +
					"或许它值得找一找看有没有珍贵的东西.";
	
	private static final float GAP	= 2;
	
	private static final int WIDTH = 120;
	
	public WndInfoItem( Heap heap ) {
		
		super();
		
		if (heap.type == Heap.Type.HEAP || heap.type == Heap.Type.FOR_SALE) {
			
			Item item = heap.peek();
			
			int color = TITLE_COLOR;
			if (item.levelKnown && item.level > 0) {
				color = ItemSlot.UPGRADED;
			} else if (item.levelKnown && item.level < 0) {
				color = ItemSlot.DEGRADED;
			}
			fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
			
		} else {
			
			String title;
			String info;
			
			if (heap.type == Type.CHEST || heap.type == Type.MIMIC) {
				title = TTL_CHEST;
				info = TXT_WONT_KNOW;
			} else if (heap.type == Type.TOMB) {
				title = TTL_TOMB;
				int thisTomb = heap.pos % 25;
				switch (thisTomb) {
					case 0:
						info = "'Gnoll Trickster, humanitarian, hero... left behind wife and 12 children.'\n\n" + TXT_OWNER;
						break;
					case 1:
						info = "'Here lies Fred, once a hero, now he's dead'\n\n" + TXT_OWNER;
						break;
					case 2:
						info = "'Jokes over. Let me out now!'\n\n" + TXT_OWNER;
						break;
					case 3:
						info = "'I’ll pull the lever.'\n\n" + TXT_OWNER;
						break;
					case 4:
						info = "'Here lies Tom, he loved bacon.  Oh, and his wife and kids too.'\n\n" + TXT_OWNER;
						break;
					case 5:
						info = "'Here lies my husband Tom, now I know where he is every night.'\n\n" + TXT_OWNER;
						break;
					case 6:
						info = "'What do you mean the whole room I'm in detects as a trap?'\n\n" + TXT_OWNER;
						break;
					case 7:
						info = "'Here lies Carl.  The second fastest draw in the west.'\n\n" + TXT_OWNER;
						break;
					case 8:
						info = "'Here lies Rick, he forgot to use the door trick.'\n\n" + TXT_OWNER;
						break;
					case 9:
						info = "'This was not in my job description'\n\n" + TXT_OWNER;
						break;
					case 10:
						info = "'There’s a smell of gas, huh? Well, my lantern is hooded. It ought to be safe.'\n\n" + TXT_OWNER;
						break;
					case 11:
						info = "'I made some good deals and I made some bad ones.  I really went in the hole with this one.'\n\n" + TXT_OWNER;
						break;
					case 12:
						info = "'A ballista? What’s that? How many dice of damage does it do?'\n\n" + TXT_OWNER;
						break;
					case 13:
						info = "'That was not a potion of healing.'\n\n" + TXT_OWNER;
						break;
					case 14:
						info = "'I came here without being consulted and I leave without my consent.'\n\n" + TXT_OWNER;
						break;
					case 15:
						info = "'I go through the door… Wait, I check for traps!'\n\n" + TXT_OWNER;
						break;
					case 16:
						info = "'Hmm…how do I know you are the REAL Angel of Death?'\n\n" + TXT_OWNER;
						break;
					case 17:
						info = "'... well this sucks.'\n\n" + TXT_OWNER;
						break;
					case 18:
						info = "'Okay, if I max out this round and win initiative next round, maybe…'\n\n" + TXT_OWNER;
						break;
					case 19:
						info = "'but it was just a flesh wound.'\n\n" + TXT_OWNER;
						break;
					case 20:
						info = "'Let me out now!'\n\n" + TXT_OWNER;
						break;
					case 21:
						info = "'That purple robe really clashes with your burning eyes…'\n\n" + TXT_OWNER;
						break;
					case 22:
						info = "'He died like he lived, screaming and crying in the corner.'\n\n" + TXT_OWNER;
						break;
					case 23:
						info = "'Yeah, I know it’s dangerous, but think of the experience points.'\n\n" + TXT_OWNER;
						break;
					case 24:
						info = "'What do you mean with, 'Your potion of levitation wore off'?'\n\n" + TXT_OWNER;
						break;
					default:
						info = TXT_OWNER;
						break;
				}
			} else if (heap.type == Type.SKELETON) {
				title = TTL_SKELETON;
				info = TXT_SKELETON;
			} else if (heap.type == Type.REMAINS) {
				title = TTL_REMAINS;
				info = TXT_REMAINS;
			} else if (heap.type == Type.CRYSTAL_CHEST) {
				title = TTL_CRYSTAL_CHEST;
				if (heap.peek() instanceof Artifact)
					info = Messages.format( TXT_INSIDE, "an artifact" );
				else if (heap.peek() instanceof Wand)
					info = Messages.format( TXT_INSIDE, "a wand" );
				else if (heap.peek() instanceof Ring)
					info = Messages.format( TXT_INSIDE, "a ring" );
				else
					info = Messages.format( TXT_INSIDE, Utils.indefinite( heap.peek().name() ) );
			} else {
				title = TTL_LOCKED_CHEST;
				info = TXT_NEED_KEY;
			}
			
			fillFields( heap.image(), heap.glowing(), TITLE_COLOR, title, info );
			
		}
	}
	
	public WndInfoItem( Item item ) {
		
		super();
		
		int color = TITLE_COLOR;
		if (item.levelKnown && item.level > 0) {
			color = ItemSlot.UPGRADED;
		} else if (item.levelKnown && item.level < 0) {
			color = ItemSlot.DEGRADED;
		}
		
		fillFields( item.image(), item.glowing(), color, item.toString(), item.info() );
	}
	
	private void fillFields( int image, ItemSprite.Glowing glowing, int titleColor, String title, String info ) {
		
		IconTitle titlebar = new IconTitle();
		titlebar.icon( new ItemSprite( image, glowing ) );
		titlebar.label( Utils.capitalize( title ), titleColor );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		
		RenderedTextMultiline txtInfo = PixelScene.renderMultiline( info, 6 );
		txtInfo.maxWidth(WIDTH);
		txtInfo.setPos(titlebar.left(), titlebar.bottom() + GAP);
		add( txtInfo );
		
		resize( WIDTH, (int)(txtInfo.top() + txtInfo.height()) );
	}
}
