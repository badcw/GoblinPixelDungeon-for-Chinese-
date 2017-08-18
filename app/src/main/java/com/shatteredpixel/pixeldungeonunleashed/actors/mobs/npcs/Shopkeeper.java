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
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.ElmoParticle;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ShopkeeperSprite;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndBag;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndTradeItem;
import com.watabou.utils.Random;

public class Shopkeeper extends NPC {

	public static final String TXT_THIEF = "小偷，小偷！";
	private int startPos = -1;

	{
		name = "商人";
		spriteClass = ShopkeeperSprite.class;
	}
	
	@Override
	protected boolean act() {
		if (startPos == -1) {
			startPos = pos;
		}

		if (startPos != pos) {
			yell("放开我！");
			flee();
			return true;
		}
		throwItem();
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		spend( TICK );
		return true;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		flee();
	}
	
	@Override
	public void add( Buff buff ) {
		flee();
	}
	
	public void flee() {
		for (Heap heap: Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				if (Random.Int(5) == 0) {
					// looks like something got left behind in the rush to leave
					heap.type = Heap.Type.HEAP;
				} else {
					CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
					heap.destroy();
				}
			}
		}
		
		destroy();
		
		sprite.killAndErase();
		CellEmitter.get( pos ).burst(ElmoParticle.FACTORY, 6);
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public String description() {
		return
			"这个胖男人看起来更适合出现一些大城市的商业区，而不是在地牢。" +
			" 他的价格解释了他为什么喜欢在这里做生意。";
	}
	
	public static WndBag sell() {
		return GameScene.selectItem( itemSelector, WndBag.Mode.FOR_SALE, "选择一个物品出售" );
	}
	
	private static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				WndBag parentWnd = sell();
				GameScene.show( new WndTradeItem( item, parentWnd ) );
			}
		}
	};

	@Override
	public void interact() {
		sell();
	}
}
