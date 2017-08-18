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
package com.shatteredpixel.pixeldungeonunleashed.items.armor.glyphs;

import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Burning;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Frost;
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.FlameParticle;
import com.shatteredpixel.pixeldungeonunleashed.effects.particles.SnowParticle;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.Armor;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.Armor.Glyph;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSprite;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class AntiEntropy extends Glyph {

	private static final String TXT_ANTI_ENTROPY	= "反熵%s ";
	private static final String TXT_DESCRIPTION = "这件护甲能把攻击者的大量热量转移到你身上.";

	private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing( 0x0000FF );
	
	@Override
	public int proc( Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.level );
		
		if (Level.adjacent( attacker.pos, defender.pos ) && Random.Int( level + 6 ) >= 5) {
			if (Random.Int(4) == 0) {
				Buff.prolong(attacker, Frost.class, Frost.duration(attacker) * Random.Float(1f, 1.5f));
				CellEmitter.get(attacker.pos).start(SnowParticle.FACTORY, 0.2f, 6);

				if (Random.Int(2) == 0) {
					Buff.affect(defender, Burning.class).reignite(defender);
					defender.sprite.emitter().burst(FlameParticle.FACTORY, 5);
				}
			} else {
				Buff.affect(attacker, Burning.class).reignite(attacker);
				attacker.sprite.emitter().burst(FlameParticle.FACTORY, 5);

				if (Random.Int(2) == 0) {
					Buff.prolong(defender, Frost.class, Frost.duration(defender) * Random.Float(1f, 1.5f));
					CellEmitter.get(defender.pos).start(SnowParticle.FACTORY, 0.2f, 6);
				}
			}

		}
		
		return damage;
	}

	@Override
	public String glyphDescription() { return TXT_DESCRIPTION; };

	@Override
	public String name( String weaponName) {
		return String.format( TXT_ANTI_ENTROPY, weaponName );
	}

	@Override
	public Glowing glowing() {
		return BLUE;
	}
}
