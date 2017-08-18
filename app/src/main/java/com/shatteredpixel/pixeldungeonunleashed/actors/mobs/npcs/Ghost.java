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

import com.shatteredpixel.pixeldungeonunleashed.Assets;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.Journal;
import com.shatteredpixel.pixeldungeonunleashed.actors.Char;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Paralysis;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Roots;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.FetidRat;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.GnollTrickster;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.GreatCrab;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.effects.CellEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.Speck;
import com.shatteredpixel.pixeldungeonunleashed.items.Generator;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.Armor;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.Weapon;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.levels.SewerLevel;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.sprites.GhostSprite;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndQuest;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndSadGhost;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.HashSet;

public class Ghost extends NPC {

	{
		name = "悲伤幽灵";
		spriteClass = GhostSprite.class;
		
		flying = true;
		
		state = WANDERING;
	}
	
	private static final String TXT_RAT1	=
			"%s...你好...我曾经像你一样——坚强且自信… " +
			"但是我被一个肮脏的怪物杀死了…我不能离开这个地方…直到我成功复仇… " +
			"如何杀死_腐臭老鼠_，已经占据了我的生活…\n\n" +
			"它生活在这层中…到处散播污物…\n" +
			"_当心它的臭味和它身上的腐蚀粘液，腐蚀粘液可以用水洗掉..._ ";

	private static final String TXT_RAT2	=
			"拜托...帮助我...杀死这个可憎之物...\n\n" +
			"_在水边战斗...避免腐蚀粘液..._";

	private static final String TXT_GNOLL1	=
			" %s...你好 ... 我曾经像你一样——坚强且自信…" +
			"但是我被一个肮脏的怪物杀死了…我不能离开这个地方…直到我成功复仇… " +
			"如何杀死_豺狼弓箭手_，已经占据了我的生活…\n\n" +
			"它不像其它的豺狼一样，他会隐藏躲避和使用投掷武器... " +
			"_当心它手上的剧毒飞镖和燃烧飞镖，不要从远处攻击..._";

	private static final String TXT_GNOLL2	=
			"拜托...帮助我...杀死这个可憎之物...\n\n" +
			"_不要让它击中你... 尽量靠近它..._";

	private static final String TXT_CRAB1	=
			"%s...你好 ... 我曾经像你一样——坚强且自信…" +
			" 但是我被一个肮脏的怪物杀死了…我不能离开这个地方…直到我成功复仇…" +
			"如何杀死_巨钳螃蟹_，已经占据了我的生活…\n\n" +
			"它并不太老...但它有一只巨大的蟹钳和一副厚壳..." +
			"_当心它的蟹钳，你一定要出其不意地攻击它，否则你的攻击会被格挡。._";

	private static final String TXT_CRAB2	=
			"拜托...帮助我...杀死这个可憎之物...\n\n" +
			"_他总是会阻塞道路...当它看到你来了..._";
	
	public Ghost() {
		super();

		Sample.INSTANCE.load( Assets.SND_GHOST );
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
	public float speed() {
		return 0.5f;
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
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
		
		Sample.INSTANCE.play( Assets.SND_GHOST );
		
		if (Quest.given) {
			if (Quest.weapon != null) {
				if (Quest.processed) {
					GameScene.show(new WndSadGhost(this, Quest.type));
				} else {
					switch (Quest.type) {
						case 1:
						default:
							GameScene.show(new WndQuest(this, TXT_RAT2));
							break;
						case 2:
							GameScene.show(new WndQuest(this, TXT_GNOLL2));
							break;
						case 3:
							GameScene.show(new WndQuest(this, TXT_CRAB2));
							break;
					}

					int newPos = -1;
					for (int i = 0; i < 10; i++) {
						newPos = Dungeon.level.randomRespawnCell();
						if (newPos != -1) {
							break;
						}
					}
					if (newPos != -1) {

						CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
						pos = newPos;
						sprite.place(pos);
						sprite.visible = Dungeon.visible[pos];
					}
				}
			}
		} else {
			Mob questBoss;
			String txt_quest;

			switch (Quest.type){
				case 1: default:
					questBoss = new FetidRat();
					txt_quest = Utils.format(TXT_RAT1, Dungeon.hero.givenName()); break;
				case 2:
					questBoss = new GnollTrickster();
					txt_quest = Utils.format(TXT_GNOLL1, Dungeon.hero.givenName()); break;
				case 3:
					if (Dungeon.level.feeling == Level.Feeling.BURNT) {
						questBoss = new GnollTrickster();
						Quest.type = 2;
						txt_quest = Utils.format(TXT_GNOLL1, Dungeon.hero.givenName()); break;
					} else {
						questBoss = new GreatCrab();
						txt_quest = Utils.format(TXT_CRAB1, Dungeon.hero.givenName());
						break;
					}
			}

			questBoss.pos = Dungeon.level.randomRespawnCell();

			if (questBoss.pos != -1) {
				GameScene.add(questBoss);
				GameScene.show( new WndQuest( this, txt_quest ) );
				Quest.given = true;
				Journal.add( Journal.Feature.GHOST );
			}

		}
	}
	
	@Override
	public String description() {
		return
			"幽灵几乎看不见。它看起来像一束无形的微弱光线， " +
			"带着忧伤的脸。 ";
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add( Paralysis.class );
		IMMUNITIES.add( Roots.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}



	public static class Quest {
		
		private static boolean spawned;

		private static int type;

		private static boolean given;
		public static boolean processed;
		
		private static int depth;
		
		public static Weapon weapon;
		public static Armor armor;
		
		public static void reset() {
			spawned = false;
			
			weapon = null;
			armor = null;
		}
		
		private static final String NODE		= "sadGhost";
		
		private static final String SPAWNED		= "spawned";
		private static final String TYPE        = "type";
		private static final String GIVEN		= "given";
		private static final String PROCESSED	= "processed";
		private static final String DEPTH		= "depth";
		private static final String WEAPON		= "weapon";
		private static final String ARMOR		= "armor";
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				
				node.put( TYPE, type );
				
				node.put( GIVEN, given );
				node.put( DEPTH, depth );
				node.put( PROCESSED, processed);
				
				node.put( WEAPON, weapon );
				node.put( ARMOR, armor );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {
			
			Bundle node = bundle.getBundle( NODE );

			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {

				type = node.getInt(TYPE);
				given	= node.getBoolean( GIVEN );
				processed = node.getBoolean( PROCESSED );

				depth	= node.getInt( DEPTH );
				
				weapon	= (Weapon)node.get( WEAPON );
				armor	= (Armor)node.get( ARMOR );
			} else {
				reset();
			}
		}
		
		public static void spawn( SewerLevel level ) {
			if (!spawned && Dungeon.depth > 1 && Random.Int( 6 - Dungeon.depth ) == 0) {
				
				Ghost ghost = new Ghost();
				do {
					ghost.pos = level.randomRespawnCell();
				} while (ghost.pos == -1);
				level.mobs.add( ghost );
				
				spawned = true;
				//dungeon depth determines type of quest.
				//depth2=fetid rat, 3=gnoll trickster, 4=great crab
				type = Dungeon.depth-1;
				
				given = false;
				processed = false;
				depth = Dungeon.depth;

				do {
					weapon = Generator.randomWeapon(10);
				} while (weapon instanceof MissileWeapon);
				armor = Generator.randomArmor(10);

				for (int i = 1; i <= 3; i++) {
					Item another;
					do {
						another = Generator.randomWeapon(10+i);
					} while (another instanceof MissileWeapon);
					if (another.level >= weapon.level) {
						weapon = (Weapon) another;
					}
					another = Generator.randomArmor(10+i);
					if (another.level >= armor.level) {
						armor = (Armor) another;
					}
				}

				weapon.identify();
				armor.identify();
			}
		}
		
		public static void process() {
			if (spawned && given && !processed && (depth == Dungeon.depth)) {
				GLog.n("悲伤幽灵;谢谢你....来找我吧...");

				// make it easier on the Hero, tell the Ghost to try to find the Hero as well
				for (Mob mob : Dungeon.level.mobs){
					if (mob instanceof Ghost)
						mob.beckon(Dungeon.hero.pos);
					}

				Sample.INSTANCE.play( Assets.SND_GHOST );
				processed = true;
				Generator.Category.ARTIFACT.probs[10] = 1; //flags the dried rose as spawnable.
			}
		}
		
		public static void complete() {
			weapon = null;
			armor = null;
			
			Journal.remove( Journal.Feature.GHOST );
		}
	}
}
