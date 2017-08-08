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
package com.shatteredpixel.pixeldungeonunleashed.items.artifacts;


import com.shatteredpixel.pixeldungeonunleashed.Assets;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.sprites.CharSprite;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ItemSpriteSheet;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;

import java.util.ArrayList;

public class CloakOfShadows extends Artifact {

	{
		name = "Moustache of Idiocy";
		image = ItemSpriteSheet.ARTIFACT_CLOAK;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = level+5;
		partialCharge = 0;
		chargeCap = level+5;

		cooldown = 0;

		defaultAction = AC_STEALTH;

		unique = true;
		bones = false;
	}

	private boolean stealthed = false;

	public static final String AC_STEALTH = "STEALTH";

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		if (isEquipped( hero ) && charge > 1)
			actions.add(AC_STEALTH);
		return actions;
	}

	@Override
	public void execute( Hero hero, String action ) {
		if (action.equals( AC_STEALTH )) {

			if (!stealthed){
				if (!isEquipped(hero)) GLog.i("You need to equip your moustache to do that.");
				else if (cooldown > 0) GLog.i("Your moustache needs " + cooldown + " more rounds to re-energize.");
				else if (charge <= 1)  GLog.i("Your moustache hasn't recharged enough to be usable yet.");
				else {
					stealthed = true;
					hero.spend( 1f );
					hero.busy();
					Sample.INSTANCE.play(Assets.SND_MELD);
					activeBuff = activeBuff();
					activeBuff.attachTo(hero);
					if (hero.sprite.parent != null) {
						hero.sprite.parent.add(new AlphaTweener(hero.sprite, 0.4f, 0.4f));
					} else {
						hero.sprite.alpha(0.4f);
					}
					hero.sprite.operate(hero.pos);
					GLog.i("Your moustache blends you in.");
				}
			} else {
				stealthed = false;
				activeBuff.detach();
				activeBuff = null;
				hero.sprite.operate( hero.pos );
				GLog.i("You look like a goblin again.");
			}

		} else
			super.execute(hero, action);
	}

	@Override
	public void activate(Char ch){
		super.activate(ch);
		if (stealthed){
			activeBuff = activeBuff();
			activeBuff.attachTo(ch);
		}
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		if (super.doUnequip(hero, collect, single)){
			stealthed = false;
			return true;
		} else
			return false;
	}

	@Override
	protected ArtifactBuff passiveBuff() {
		return new cloakRecharge();
	}

	@Override
	protected ArtifactBuff activeBuff( ) {
		return new cloakStealth();
	}

	@Override
	public Item upgrade() {
		if (level < levelCap) {
			chargeCap++;
			return super.upgrade();
		} else {
			return this;
		}
	}

	@Override
	public String desc() {
		String desc = "This moustache made of boars hair and string is powered by sheer belief. When worn, " +
				"it can be used to hide your presence for a short time.\n\n";

		if (level < 5)
		 desc += "The moustache's magic has faded and it is not very powerful, perhaps it will regain strength through use.";
		else if (level < 10)
			desc += "The moustache's power has begun to return.";
		else if (level < 15)
			desc += "The moustache has almost returned to full strength.";
		else
			desc += "The moustache is at full potential and will work for extended durations.";


		if ( isEquipped (Dungeon.hero) )
			desc += "\n\nThe moustache sits under your nose.";


		return desc;
	}

	private static final String STEALTHED = "stealthed";
	private static final String COOLDOWN = "cooldown";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put( STEALTHED, stealthed );
		bundle.put( COOLDOWN, cooldown );
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle(bundle);
		stealthed = bundle.getBoolean( STEALTHED );
		cooldown = bundle.getInt( COOLDOWN );
	}

	public class cloakRecharge extends ArtifactBuff{
		@Override
		public boolean act() {
			if (charge < chargeCap) {
				if (!stealthed)
					partialCharge += (1f / (60 - (chargeCap-charge)*2));

				if (partialCharge >= 1) {
					charge++;
					partialCharge -= 1;
					if (charge == chargeCap){
						partialCharge = 0;
					}

				}
			} else
				partialCharge = 0;

			if (cooldown > 0)
				cooldown --;

			updateQuickslot();

			spend( TICK );

			return true;
		}

	}

	@Override
	public void updateArtifact() {
		chargeCap = level + 5;
	}


	public class cloakStealth extends ArtifactBuff{
		@Override
		public int icon() {
			return BuffIndicator.INVISIBLE;
		}

		@Override
		public boolean attachTo( Char target ) {
			if (super.attachTo( target )) {
				target.invisible++;
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean act(){
			charge--;
			if (charge <= 0) {
				detach();
				GLog.w("Your moustache has run out of energy.");
				((Hero)target).interrupt();
			}

			exp += 10 + ((Hero)target).lvl;

			if (exp >= (level+1)*50 && level < levelCap) {
				upgrade();
				exp -= level*50;
				GLog.p("Your moustache grows stronger!");
			}

			updateQuickslot();

			spend( TICK );

			return true;
		}

		@Override
		public void fx(boolean on) {
			if (on) target.sprite.add( CharSprite.State.INVISIBLE );
			else if (target.invisible == 0) target.sprite.remove( CharSprite.State.INVISIBLE );
		}

		@Override
		public String toString() {
			return "Camouflaged";
		}

		@Override
		public String desc() {
			return "Your moustache of idiocy is granting you invisibility while you are wearing it.\n" +
					"\n" +
					"While you are camouflaged enemies ignore you as one of their own. " +
					"Most physical attacks and magical effects (such as scrolls and wands) will immediately cancel camouflage.\n" +
					"\n" +
					"You will remain camouflaged until it is cancelled or your moustache runs out of charge.";
		}

		@Override
		public void detach() {
			if (target.invisible > 0)
				target.invisible--;
			stealthed = false;
			cooldown = 10 - (level / 3);

			updateQuickslot();
			super.detach();
		}
	}

}