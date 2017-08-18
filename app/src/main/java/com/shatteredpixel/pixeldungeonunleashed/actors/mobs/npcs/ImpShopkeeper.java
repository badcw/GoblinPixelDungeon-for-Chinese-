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
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.Speck;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.ElmoParticle;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ImpSprite;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.shatteredpixel.pixeldungeonunleashed.messages.Messages;

public class ImpShopkeeper extends Shopkeeper {

	private static final String TXT_GREETINGS = "又见面了！%s！";
	public static final String TXT_THIEF = "我以为我可以信任你！";

	
	{
		name = "雄心勃勃的小恶魔";
		spriteClass = ImpSprite.class;
	}
	
	private boolean seenBefore = false;
	
	@Override
	protected boolean act() {

		if (!seenBefore && Dungeon.visible[pos]) {
			yell( Messages.format( TXT_GREETINGS, Dungeon.hero.givenName() ) );
			seenBefore = true;
		}
		
		return super.act();
	}
	
	@Override
	public void flee() {
		for (Heap heap: Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				CellEmitter.get( heap.pos ).burst( ElmoParticle.FACTORY, 4 );
				heap.destroy();
			}
		}
		
		destroy();
		
		sprite.emitter().burst( Speck.factory( Speck.WOOL ), 15 );
		sprite.killAndErase();
	}
	
	@Override
	public String description() {
		return
				"小恶魔比其它恶魔更小。他们既没有攻击力，也没有魔法天赋， " +
						"但他们很聪明，善于交际。许多小恶魔都喜欢生活在非恶魔层。";
	}
}
