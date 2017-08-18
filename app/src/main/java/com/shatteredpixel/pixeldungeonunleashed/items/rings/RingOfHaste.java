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
package com.shatteredpixel.pixeldungeonunleashed.items.rings;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.pets.PET;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndPetHaste;

public class RingOfHaste extends Ring {

	{
		name = "疾速之戒";
	}

	@Override
	protected RingBuff buff( ) {
		return new Haste();
	}

	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}
		return null;
	}

	@Override
	public String desc() {
		return isKnown() ?
				"这枚戒指减轻了佩戴者的负担，使其能够飞速奔跑" +
						"负等级的戒指会让佩戴者倍感沉重。":
				super.desc();
	}

	public class Haste extends RingBuff {
/*
		// I have to think about this one... do we want the Buff Icon to display for unidentified rings?
		@Override
		public int icon() {
			return BuffIndicator.HASTED;
		}

		@Override
		public String toString() {
			return "Hasted";
		}

		@Override
		public String desc() {
			return "急速的魔法会影响目标的时间，对他们来说一切都是缓慢的。\n\n" +
					"使角色更快地执行所有的动作。\n\n" +
					"这个效果是由你的戒戒持续的。";
		}
		*/
	}

	@Override
	public boolean doEquip(Hero hero) {

		PET heropet = checkpet();


		if (Dungeon.hero.haspet && heropet!=null && isKnown()){
			GameScene.show(new WndPetHaste(heropet,this));
		}

		return super.doEquip(hero);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {

		PET heropet = checkpet();

		if (Dungeon.hero.haspet && heropet!=null&&Dungeon.petHasteLevel>0){
			Dungeon.petHasteLevel=0;
			GLog.w("Your "+heropet.name+" is moving slower." );
		}

		return super.doUnequip(hero, collect, single);

	}
}
