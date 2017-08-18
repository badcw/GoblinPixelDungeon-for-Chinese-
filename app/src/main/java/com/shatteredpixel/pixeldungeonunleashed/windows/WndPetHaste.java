/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.pets.PET;
import com.shatteredpixel.pixeldungeonunleashed.items.rings.RingOfHaste;
import com.shatteredpixel.pixeldungeonunleashed.scenes.PixelScene;
import com.shatteredpixel.pixeldungeonunleashed.ui.RedButton;
import com.shatteredpixel.pixeldungeonunleashed.ui.Window;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;
import com.shatteredpixel.pixeldungeonunleashed.ui.NewRedButton;
import com.shatteredpixel.pixeldungeonunleashed.ui.RenderedTextMultiline;


public class WndPetHaste extends Window {
	
	//if people don't get it after this, I quit. I just quit.

	private static final String TXT_MESSAGE = "你可以将你极速状态的一部分分给你的宠物 " +
			"这将限制你的极速等级为十,但是你的宠物可以跟得上. "
			+"你可以摘除你的极速戒指来使你的宠物失去极速";

	private static final String TXT_YES = "加快我的宠物";
	private static final String TXT_NO = "不，谢了";


	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndPetHaste(final PET pet, final RingOfHaste ring) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(pet.sprite());
		titlebar.label(Utils.capitalize(pet.name));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		RenderedTextMultiline message = PixelScene
				.renderMultiline(TXT_MESSAGE, 6);
		message.maxWidth(WIDTH);
		message.setPos(0,titlebar.bottom() + GAP);
		add(message);

		NewRedButton btnBattle = new NewRedButton(TXT_YES) {
			@Override
			protected void onClick() {
				Dungeon.petHasteLevel=ring.level;
				GLog.p("Your "+pet.name+" is moving faster!");
				hide();
			}
		};
		btnBattle.setRect(0, message.top() + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);

		NewRedButton btnNonBattle = new NewRedButton(TXT_NO+" "+ring.level+" "+pet.speed()) {
			@Override
			protected void onClick() {
				hide();
			}
		};
		
		btnNonBattle.setRect(0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle);
		
		
		resize(WIDTH, (int) btnNonBattle.bottom());
	}

	
	
	
}
