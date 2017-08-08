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
package com.shatteredpixel.pixeldungeonunleashed.actors.buffs;

import com.shatteredpixel.pixeldungeonunleashed.sprites.CharSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;

public class Corruption extends Buff {

	{
		type = buffType.NEGATIVE;
	}

	private float buildToDamage = 0f;

	@Override
	public boolean act() {
		buildToDamage += target.HT/100f;

		int damage = (int)buildToDamage;
		buildToDamage -= damage;

		if (damage > 0)
			target.damage(damage, this);

		spend(TICK);

		return true;
	}

	@Override
	public void fx(boolean on) {
		if (on) target.sprite.add( CharSprite.State.DARKENED );
		else if (target.invisible == 0) target.sprite.remove( CharSprite.State.DARKENED );
	}

	@Override
	public int icon() {
		return BuffIndicator.CORRUPT;
	}

	@Override
	public String toString() {
		return "腐化";
	}

	@Override
	public String desc() {
		return "腐化渗透到生物的内心中，扭曲了他们的过去本性。\n" +
				"\n" +
				"被腐化的生物会攻击和激怒他们的盟友，而忽略他们以前的敌人。 " +
				"腐化也具有破坏性，会慢慢使目标死亡。\n" +
				"\n" +
				"腐化是永久性的，只会因死亡的影响而结束。";
	}
}
