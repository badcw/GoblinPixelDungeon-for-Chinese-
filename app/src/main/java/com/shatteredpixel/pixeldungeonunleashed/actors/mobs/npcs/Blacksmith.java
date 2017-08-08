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

import java.util.Collection;

import com.watabou.noosa.audio.Sample;
import com.shatteredpixel.pixeldungeonunleashed.Assets;
import com.shatteredpixel.pixeldungeonunleashed.Badges;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.Journal;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.items.EquipableItem;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.quest.DarkGold;
import com.shatteredpixel.pixeldungeonunleashed.items.quest.Pickaxe;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.pixeldungeonunleashed.levels.Room;
import com.shatteredpixel.pixeldungeonunleashed.levels.Room.Type;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.BlacksmithSprite;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndBlacksmith;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Blacksmith extends NPC {

	private static final String TXT_GOLD_1 =
		"嘿，哥布林！你能帮我做些事吗?我会给你报酬。用这把稿子去挖_暗金矿_，我想_15块_应该就足够了。 " +
		"你是什么意思，我该怎么付钱？你太贪婪了...\n" +
		"好吧，好吧，我没钱付给你, 但是我可以帮你锻造装备。我觉得我很幸运， " +
		"毕竟我是附近唯一的铁匠。";
	private static final String TXT_BLOOD_1 =
		"嘿，哥布林! 你能帮我做些事吗?我会给你报酬。 用这把稿子去_杀一只蝙蝠_,我需要它头上的血。 " +
		"你是什么意思，我该怎么付钱？你太贪婪了...\n" +
		"好吧，好吧，我没钱付给你, 但是我可以帮你锻造装备。我觉得我很幸运， " +
		"毕竟我是附近唯一的铁匠。";
	private static final String TXT2 =
		"你是在跟我开玩笑吧？我的稿子在哪里?!";
	private static final String TXT3 =
		"挖15块暗金矿，真的很难吗？";
	private static final String TXT4 =
		"我说我需要蝙蝠血粘在稿子上。快去！";
	private static final String TXT_COMPLETED =
		"哦，你终于回来了...好迟啊。";
	private static final String TXT_GET_LOST =
		"我很忙，请你离开吧。";
	
	private static final String TXT_LOOKS_BETTER	= "你的 %s 现在看起来更好了。";
	
	{
		name = "巨魔铁匠";
		spriteClass = BlacksmithSprite.class;
	}
	
	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	@Override
	public void interact() {
		
		sprite.turnTo( pos, Dungeon.hero.pos );
		
		if (!Quest.given) {
			
			GameScene.show( new WndQuest( this,
				Quest.alternative ? TXT_BLOOD_1 : TXT_GOLD_1 ) {
				
				@Override
				public void onBackPressed() {
					super.onBackPressed();
					
					Quest.given = true;
					Quest.completed = false;
					
					Pickaxe pick = new Pickaxe();
					if (pick.doPickUp( Dungeon.hero )) {
						GLog.i( Hero.TXT_YOU_NOW_HAVE, pick.name() );
					} else {
						Dungeon.level.drop( pick, Dungeon.hero.pos ).sprite.drop();
					}
				};
			} );
			
			Journal.add( Journal.Feature.TROLL );
			
		} else if (!Quest.completed) {
			if (Quest.alternative) {
				
				Pickaxe pick = Dungeon.hero.belongings.getItem( Pickaxe.class );
				if (pick == null) {
					tell( TXT2 );
				} else if (!pick.bloodStained) {
					tell( TXT4 );
				} else {
					if (pick.isEquipped( Dungeon.hero )) {
						pick.doUnequip( Dungeon.hero, false );
					}
					pick.detach( Dungeon.hero.belongings.backpack );
					tell( TXT_COMPLETED );
					
					Quest.completed = true;
					Quest.reforged = false;
				}
				
			} else {
				
				Pickaxe pick = Dungeon.hero.belongings.getItem( Pickaxe.class );
				DarkGold gold = Dungeon.hero.belongings.getItem( DarkGold.class );
				if (pick == null) {
					tell( TXT2 );
				} else if (gold == null || gold.quantity() < 15) {
					tell( TXT3 );
				} else {
					if (pick.isEquipped( Dungeon.hero )) {
						pick.doUnequip( Dungeon.hero, false );
					}
					pick.detach( Dungeon.hero.belongings.backpack );
					gold.detachAll( Dungeon.hero.belongings.backpack );
					tell( TXT_COMPLETED );
					
					Quest.completed = true;
					Quest.reforged = false;
				}
				
			}
		} else if (!Quest.reforged) {
			
			GameScene.show( new WndBlacksmith( this, Dungeon.hero ) );
			
		} else {
			
			tell( TXT_GET_LOST );
			
		}
	}
	
	private void tell( String text ) {
		GameScene.show( new WndQuest( this, text ) );
	}
	
	public static String verify( Item item1, Item item2 ) {
		
		if (item1 == item2) {
			return "选择2个不同的物品，而不是同一个物品两次！";
		}
/*
		// Removed to make forging more interesting to upgrade any good item with something else.
		if (!item1.isIdentified() || !item2.isIdentified()) {
			return "I need to know what I'm working with, identify dat first!";
		}
*/
		if (item1.level+item2.level > item1.levelCap) {
            return "这个物品将会太强大了！";
        }

		if (item1.cursed || item2.cursed) {
			return "我不接收被诅咒的物品！";
		}
		
		if (item1.level < 0 || item2.level < 0) {
			return "这简直就是垃圾，质量太差了！";
		}
		
		if (!item1.isUpgradable() || !item2.isUpgradable()) {
			return "我不能再重新锻造这些物品！";
		}
		
		return null;
	}
	
	public static void upgrade( Item item1, Item item2 ) {
		
		Item first, second;
		if (item2.level > item1.level) {
			first = item2;
			second = item1;
		} else {
			first = item1;
			second = item2;
		}

		Sample.INSTANCE.play( Assets.SND_EVOKE );
		ScrollOfUpgrade.upgrade( Dungeon.hero );
		Item.evoke( Dungeon.hero );
		
		if (first.isEquipped( Dungeon.hero )) {
			((EquipableItem)first).doUnequip( Dungeon.hero, true );
		}
		first.upgrade(first.level+second.level+1);
		GLog.p( TXT_LOOKS_BETTER, first.name() );
		Dungeon.hero.spendAndNext( 2f );
		Badges.validateItemLevelAquired( first );
		
		if (second.isEquipped( Dungeon.hero )) {
			((EquipableItem)second).doUnequip( Dungeon.hero, false );
		}
		second.detachAll( Dungeon.hero.belongings.backpack );
		
		Quest.reforged = true;
		
		Journal.remove( Journal.Feature.TROLL );
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 1000;
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
	public String description() {
		return
			"这个巨魔铁匠比其它所有巨魔看起来：更高大且精瘦，他的皮肤像石头" +
			"的颜色和纹理。巨魔铁匠正在不按比例地混合锻造物品。";
	}

	public static class Quest {
		
		private static boolean spawned;
		
		private static boolean alternative;
		private static boolean given;
		private static boolean completed;
		private static boolean reforged;
		
		public static void reset() {
			spawned		= false;
			given		= false;
			completed	= false;
			reforged	= false;
		}
		
		private static final String NODE	= "blacksmith";
		
		private static final String SPAWNED		= "spawned";
		private static final String ALTERNATIVE	= "alternative";
		private static final String GIVEN		= "given";
		private static final String COMPLETED	= "completed";
		private static final String REFORGED	= "reforged";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				node.put( ALTERNATIVE, alternative );
				node.put( GIVEN, given );
				node.put( COMPLETED, completed );
				node.put( REFORGED, reforged );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				alternative	=  node.getBoolean( ALTERNATIVE );
				given = node.getBoolean( GIVEN );
				completed = node.getBoolean( COMPLETED );
				reforged = node.getBoolean( REFORGED );
			} else {
				reset();
			}
		}
		
		public static boolean spawn( Collection<Room> rooms ) {
			if (!spawned && Dungeon.depth > 13 && Random.Int( 18 - Dungeon.depth ) == 0) {
				
				Room blacksmith = null;
				for (Room r : rooms) {
					if (r.type == Type.STANDARD && r.width() > 4 && r.height() > 4) {
						blacksmith = r;
						blacksmith.type = Type.BLACKSMITH;
						
						spawned = true;
						alternative = Random.Int( 2 ) == 0;
						
						given = false;
						
						break;
					}
				}
			}
			return spawned;
		}
	}
}
