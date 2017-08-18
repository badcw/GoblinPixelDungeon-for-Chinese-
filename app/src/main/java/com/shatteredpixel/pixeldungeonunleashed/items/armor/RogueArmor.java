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
package com.shatteredpixel.pixeldungeonunleashed.items.armor;

import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfTeleportation;
import com.watabou.noosa.audio.Sample;
import com.shatteredpixel.pixeldungeonunleashed.Assets;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Blindness;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.HeroClass;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.Speck;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.scenes.CellSelector;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;

public class RogueArmor extends ClassArmor {

	private static final String TXT_FOV 		= "你只能闪现到你视野中的空地！";
	private static final String TXT_NOT_ROGUE	= "只有 盗贼能使用这件护甲 !";

	private static final String AC_SPECIAL = "闪现";

	{
		name = "暗影披风";
		image = ItemSpriteSheet.ARMOR_ROGUE;
	}
	
	@Override
	public String special() {
		return AC_SPECIAL;
	}
	
	@Override
	public void doSpecial() {
		GameScene.selectCell( teleporter );
	}
	
	@Override
	public boolean doEquip( Hero hero ) {
		if (hero.heroClass == HeroClass.FUMBLES) {
			return super.doEquip( hero );
		} else {
			GLog.w( TXT_NOT_ROGUE );
			return false;
		}
	}
	
	@Override
	public String desc() {
		return
				"穿着这件暗影披风, 能表演一个把戏: \"闪现\" " +
						"(没用多少炸药): 他能闪瞎能看到他的敌人的眼睛并跳开。";
	}
	
	protected static CellSelector.Listener teleporter = new  CellSelector.Listener() {
		
		@Override
		public void onSelect( Integer target ) {
			if (target != null) {

				if (!Level.fieldOfView[target] ||
					!(Level.passable[target] || Level.avoid[target]) ||
					Actor.findChar( target ) != null) {
					
					GLog.w( TXT_FOV );
					return;
				}

				curUser.HP -= (curUser.HP / 3);
				
				for (Mob mob : Dungeon.level.mobs) {
					if (Level.fieldOfView[mob.pos]) {
						Buff.prolong( mob, Blindness.class, 2 );
						if (mob.state == mob.HUNTING) mob.state = mob.WANDERING;
						mob.sprite.emitter().burst( Speck.factory( Speck.LIGHT ), 4 );
					}
				}
				
				ScrollOfTeleportation.appear( curUser, target );
				CellEmitter.get( target ).burst( Speck.factory( Speck.WOOL ), 10 );
				Sample.INSTANCE.play( Assets.SND_PUFF );
				Dungeon.level.press( target, curUser );
				Dungeon.observe();
				
				curUser.spendAndNext( Actor.TICK );
			}
		}
		
		@Override
		public String prompt() {
			return "选择要闪现的到地方";
		}
	};
}