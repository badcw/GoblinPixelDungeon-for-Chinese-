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
import com.shatteredpixel.pixeldungeonunleashed.Journal;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Golem;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Monk;
import com.shatteredpixel.pixeldungeonunleashed.items.Generator;
import com.shatteredpixel.pixeldungeonunleashed.items.quest.DwarfToken;
import com.shatteredpixel.pixeldungeonunleashed.items.rings.Ring;
import com.shatteredpixel.pixeldungeonunleashed.levels.CityLevel;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.ImpSprite;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndImp;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
import com.shatteredpixel.pixeldungeonunleashed.messages.Messages;

public class Imp extends NPC {

	{
		name = "雄心勃勃的小恶魔";
		spriteClass = ImpSprite.class;
	}
	
	private static final String TXT_GOLEMS1	=
		"你是冒险者吗？我爱冒险者！ 如果需要杀死某些东西，" +
		"你们总是可以信赖的。我说得对吗？当然，我这么做是为了奖金。\n" +
		"对我来说要杀的是_魔像_ 。你看，我将要在这里开始做一些" +
		"小生意， 但是这些魔像阻碍了我的发展! " +
		"我很难与这些四处游荡的花岗岩块谈判，该死的！" +
		"所以，拜托，杀了它们...只要杀_6个_,奖励就是你的。";
	
	private static final String TXT_MONKS1	=
		"你是冒险者吗？我爱冒险者！ 如果需要杀死某些东西， " +
		"你们总是可以信赖的。我说得对吗？当然，我这么做是为了奖金。\n" +
		"对我来说要杀的是_武僧_ 。你看，我将要在这里开始做一些" +
		"小生意，但这些疯子不买任何东西" +
		"还会吓跑其它顾客。 " +
		"所以，拜托，杀了它们...只要杀_8个_,奖励就是你的。.";
	
	private static final String TXT_GOLEMS2	=
		"魔像捕猎得怎么样？";
	
	private static final String TXT_MONKS2	=
		"哦，你竟然还活着！我知道你的功夫更强壮。 " +
		"只是不要忘记抓住这些武僧身上的徽章。";
	
	private static final String TXT_CYA	= "再见, %s!";
	private static final String TXT_HEY	= "喂, %s!";
	
	private boolean seenBefore = false;
	
	@Override
	protected boolean act() {
		
		if (!Quest.given && Dungeon.visible[pos]) {
			if (!seenBefore) {
				yell( Messages.format( TXT_HEY, Dungeon.hero.givenName() ) );
			}
			seenBefore = true;
		} else {
			seenBefore = false;
		}
		
		throwItem();
		
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
	}
	
	@Override
	public String defenseVerb() {
		return "格挡";
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public void interact() {
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		if (Quest.given) {
			
			DwarfToken tokens = Dungeon.hero.belongings.getItem( DwarfToken.class );
			if (tokens != null && (tokens.quantity() >= 8 || (!Quest.alternative && tokens.quantity() >= 6))) {
				GameScene.show( new WndImp( this, tokens ) );
			} else {
				tell( Quest.alternative ? TXT_MONKS2 : TXT_GOLEMS2, Dungeon.hero.givenName() );
			}
			
		} else {
			tell( Quest.alternative ? TXT_MONKS1 : TXT_GOLEMS1 );
			Quest.given = true;
			Quest.completed = false;
			
			Journal.add( Journal.Feature.IMP );
		}
	}
	
	private void tell( String format, Object...args ) {
		GameScene.show(
			new WndQuest( this, Messages.get( format, args ) ) );
	}
	
	public void flee() {
		
		yell( Messages.format( TXT_CYA, Dungeon.hero.givenName() ) );
		
		destroy();
		sprite.die();
	}
	
	@Override
	public String description() {
		return
			"小恶魔比其它恶魔更小。他们既没有攻击力，也没有魔法天赋， " +
			"但他们很聪明，善于交际。许多小恶魔都喜欢生活在非恶魔层。";
	}
	
	public static class Quest {
		
		private static boolean alternative;
		
		private static boolean spawned;
		private static boolean given;
		private static boolean completed;
		
		public static Ring reward;
		
		public static void reset() {
			spawned = false;

			reward = null;
		}
		
		private static final String NODE		= "demon";
		
		private static final String ALTERNATIVE	= "alternative";
		private static final String SPAWNED		= "spawned";
		private static final String GIVEN		= "given";
		private static final String COMPLETED	= "completed";
		private static final String REWARD		= "reward";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				node.put( ALTERNATIVE, alternative );
				
				node.put( GIVEN, given );
				node.put( COMPLETED, completed );
				node.put( REWARD, reward );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				alternative	= node.getBoolean( ALTERNATIVE );
				
				given = node.getBoolean( GIVEN );
				completed = node.getBoolean( COMPLETED );
				reward = (Ring)node.get( REWARD );
			}
		}
		
		public static void spawn( CityLevel level ) {
			if (!spawned && Dungeon.depth > 19 && Random.Int( 24 - Dungeon.depth ) == 0) {
				
				Imp npc = new Imp();
				do {
					npc.pos = level.randomRespawnCell();
				} while (npc.pos == -1 || level.heaps.get( npc.pos ) != null);
				level.mobs.add( npc );
				
				spawned = true;
				alternative = Random.Int( 2 ) == 0;
				
				given = false;
				
				do {
					reward = (Ring)Generator.random( Generator.Category.RING );
				} while (reward.cursed);
				reward.upgrade( 2 );
				reward.cursed = true;
			}
		}
		
		public static void process( Mob mob ) {
			if (spawned && given && !completed) {
				if ((alternative && mob instanceof Monk) ||
					(!alternative && mob instanceof Golem)) {
					
					Dungeon.level.drop( new DwarfToken(), mob.pos ).sprite.drop();
				}
			}
		}
		
		public static void complete() {
			reward = null;
			completed = true;
			
			Journal.remove( Journal.Feature.IMP );
		}
		
		public static boolean isCompleted() {
			return completed;
		}
	}
}
