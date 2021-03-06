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
package com.shatteredpixel.pixeldungeonunleashed.levels.features;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.food.Yumyuck;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndBag;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndOptions;

import java.util.Iterator;

public class AlchemyPot {

	private static final String TXT_SELECT_SEED	= "选择一个种子扔进去";
	private static final String TXT_POT	        = "炼药釜";
	private static final String TXT_FRUIT	    = "煮无味果";
	private static final String TXT_POTION	    = "炼制药水";
	private static final String TXT_OPTIONS	    =
			"你想用一个种子和无味果一起煮，或者用一些种子来酿造药水吗?";
	
	public static Hero hero;
	public static int pos;

	public static boolean foundFruit;
	public static Item curItem = null;
	
	public static void operate( Hero hero, int pos ) {
		
		AlchemyPot.hero = hero;
		AlchemyPot.pos = pos;

		Iterator<Item> items = hero.belongings.iterator();
		foundFruit = false;
		Heap heap = Dungeon.level.heaps.get( pos );

		if (heap == null)
			while (items.hasNext() && !foundFruit){
				curItem = items.next();
				if (curItem instanceof Yumyuck && ((Yumyuck) curItem).potionAttrib == null){
						GameScene.show(
								new WndOptions(TXT_POT, TXT_OPTIONS, TXT_FRUIT, TXT_POTION) {
									@Override
									protected void onSelect(int index) {
										if (index == 0) {
											curItem.cast( AlchemyPot.hero, AlchemyPot.pos );
										} else
											GameScene.selectItem(itemSelector, WndBag.Mode.SEED, TXT_SELECT_SEED);
									}
								}
					);
					foundFruit = true;
				}
			}

		if (!foundFruit)
			GameScene.selectItem(itemSelector, WndBag.Mode.SEED, TXT_SELECT_SEED);
	}
	
	private static final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				item.cast( hero, pos );
			}
		}
	};
}
