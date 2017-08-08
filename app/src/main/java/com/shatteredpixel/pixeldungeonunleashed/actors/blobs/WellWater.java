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
package com.shatteredpixel.pixeldungeonunleashed.actors.blobs;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.Journal;
import com.shatteredpixel.pixeldungeonunleashed.Journal.Feature;
import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.levels.Terrain;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndMessage;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class WellWater extends Blob {

	protected int pos;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
		for (int i=0; i < LENGTH; i++) {
			if (cur[i] > 0) {
				pos = i;
				break;
			}
		}
	}
	
	@Override
	protected void evolve() {
		volume = off[pos] = cur[pos];
		
		if (Dungeon.visible[pos]) {
			if (this instanceof WaterOfAwareness) {
				Journal.add( Feature.WELL_OF_AWARENESS );
				if (Dungeon.difficultyLevel == Dungeon.DIFF_TUTOR && !Dungeon.tutorial_wellA_seen) {
					Dungeon.tutorial_wellA_seen = true;
					GameScene.show(new WndMessage("从察觉之井中微微啜饮一口就可以鉴定你的装备(也只有 " +
						"你的装备) , 并揭示这层的秘密, 和任何未知的物品。 " +
						"或者,你也可以尝试把一个未鉴定的物品丢进去，看看会发生什么。 "));
				}
			} else if (this instanceof WaterOfHealth && !Dungeon.tutorial_wellH_seen) {
				Dungeon.tutorial_wellH_seen = true;
				Journal.add( Feature.WELL_OF_HEALTH );
				if (Dungeon.difficultyLevel == Dungeon.DIFF_TUTOR) {
					GameScene.show(new WndMessage(" 从生命之井中微微啜饮一口能使你恢复健康，并" +
						"解除你的饥饿感。"));
				}
			} else if (this instanceof WaterOfTransmutation && !Dungeon.tutorial_wellT_seen) {
				Dungeon.tutorial_wellT_seen = true;
				Journal.add( Feature.WELL_OF_TRANSMUTATION );
				if (Dungeon.difficultyLevel == Dungeon.DIFF_TUTOR) {
					GameScene.show(new WndMessage("嬗变之井允许你把一个物品转换成另一个" +
						" 相似价值的物品; 一个3级的附魔的武器转换成另一个不同的3级附魔的武器,一个神器转换成" +
						" 另一个神器。有些稀有的物品只能通过嬗变之井获得。"));
				}
			}
		}
	}
	
	protected boolean affect() {

		Heap heap;
		
		if (pos == Dungeon.hero.pos && affectHero( Dungeon.hero )) {
			
			volume = off[pos] = cur[pos] = 0;
			return true;
			
		} else if ((heap = Dungeon.level.heaps.get( pos )) != null) {
			
			Item oldItem = heap.peek();
			Item newItem = affectItem( oldItem );
			
			if (newItem != null) {
				
				if (newItem == oldItem) {

				} else if (oldItem.quantity() > 1) {

					oldItem.quantity( oldItem.quantity() - 1 );
					heap.drop( newItem );
					
				} else {
					heap.replace( oldItem, newItem );
				}
				
				heap.sprite.link();
				volume = off[pos] = cur[pos] = 0;
				
				return true;
				
			} else {
				
				int newPlace;
				do {
					newPlace = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
				} while (!Level.passable[newPlace] && !Level.avoid[newPlace] && Actor.findChar(newPlace) == null);
				Dungeon.level.drop( heap.pickUp(), newPlace ).sprite.drop( pos );
				
				return false;
				
			}
			
		} else {
			
			return false;
			
		}
	}
	
	protected boolean affectHero( Hero hero ) {
		return false;
	}
	
	protected Item affectItem( Item item ) {
		return null;
	}
	
	@Override
	public void seed( int cell, int amount ) {
		cur[pos] = 0;
		pos = cell;
		volume = cur[pos] = amount;
	}
	
	public static void affectCell( int cell ) {
		
		Class<?>[] waters = {WaterOfHealth.class, WaterOfAwareness.class, WaterOfTransmutation.class};
		
		for (Class<?>waterClass : waters) {
			WellWater water = (WellWater)Dungeon.level.blobs.get( waterClass );
			if (water != null &&
				water.volume > 0 &&
				water.pos == cell &&
				water.affect()) {
				
				Level.set( cell, Terrain.EMPTY_WELL );
				GameScene.updateMap( cell );
				
				return;
			}
		}
	}
}
