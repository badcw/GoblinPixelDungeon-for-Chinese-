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

import com.shatteredpixel.pixeldungeonunleashed.Challenges;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.npcs.Ghost;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.scenes.PixelScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.FetidRatSprite;
import com.shatteredpixel.pixeldungeonunleashed.sprites.GnollTricksterSprite;
import com.shatteredpixel.pixeldungeonunleashed.sprites.GreatCrabSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.RedButton;
import com.shatteredpixel.pixeldungeonunleashed.ui.Window;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.ui.NewRedButton;
import com.shatteredpixel.pixeldungeonunleashed.ui.RenderedTextMultiline;
import com.watabou.noosa.BitmapTextMultiline;

public class WndSadGhost extends Window {

	private static final String TXT_RAT	=
			"谢谢你, 那只可怕的老鼠被杀了,我可以休息了..." +
					"我不知道是什么扭曲的魔法创造了一个这样黑暗的生物...\n\n";
	private static final String TXT_GNOLL	=
			"谢谢你, 那只狡诈的豺狼被杀了,我可以休息了..." +
					"我想知道是什么扭曲的魔法把它变得这么聪明...\n\n";
	private static final String TXT_CRAB	=
			"谢谢你, 那只巨大的螃蟹被杀了,我可以休息了..." +
					"我想知道是什么扭曲的魔法使它能活的这么久...\n\n";
	private static final String TXT_GIVEITEM=
			"请带走这些东西的一个，它们现在对我没用... " +
					"也许它们会在旅途中帮到你...\n\n" +
			"另外... 地牢里有一件我非常珍爱的东西..." +
			"如果你能...找到我的... 玫瑰......";
	private static final String TXT_WEAPON	= "幽灵的武器";
	private static final String TXT_ARMOR	= "幽灵的防具";
	
	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;
	
	public WndSadGhost( final Ghost ghost, final int type ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		RenderedTextMultiline message;
		switch (type){
			case 1:default:
				titlebar.icon( new FetidRatSprite() );
				titlebar.label( "DEFEATED FETID RAT" );
				message = PixelScene.renderMultiline( TXT_RAT+TXT_GIVEITEM, 6 );
				break;
			case 2:
				titlebar.icon( new GnollTricksterSprite() );
				titlebar.label( "DEFEATED GNOLL TRICKSTER" );
				message = PixelScene.renderMultiline( TXT_GNOLL+TXT_GIVEITEM, 6 );
				break;
			case 3:
				titlebar.icon( new GreatCrabSprite());
				titlebar.label( "DEFEATED GREAT CRAB" );
				message = PixelScene.renderMultiline( TXT_CRAB+TXT_GIVEITEM, 6 );
				break;

		}


		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);
		
		NewRedButton btnWeapon = new NewRedButton( TXT_WEAPON ) {
			@Override
			protected void onClick() {
				selectReward( ghost, Ghost.Quest.weapon );
			}
		};
		btnWeapon.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnWeapon );

		if (!Dungeon.isChallenged( Challenges.NO_ARMOR )) {
			NewRedButton btnArmor = new NewRedButton(TXT_ARMOR) {
				@Override
				protected void onClick() {
					selectReward(ghost, Ghost.Quest.armor);
				}
			};
			btnArmor.setRect(0, btnWeapon.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnArmor);

			resize(WIDTH, (int) btnArmor.bottom());
		} else {
			resize(WIDTH, (int) btnWeapon.bottom());
		}
	}
	
	private void selectReward( Ghost ghost, Item reward ) {
		
		hide();
		
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Hero.TXT_YOU_NOW_HAVE, reward.name() );
		} else {
			Dungeon.level.drop( reward, ghost.pos ).sprite.drop();
		}
		
		ghost.yell( "再见,冒险者!!" );
		ghost.die( null );
		
		Ghost.Quest.complete();
	}
}
