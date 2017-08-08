/*
 * Unleashed Pixel Dungeon
 * Copyright (C) 2015  David Mitchell
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
package com.shatteredpixel.pixeldungeonunleashed.actors.blobs;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.Journal;
import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Awareness;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Bless;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Burning;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Paralysis;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Vertigo;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.effects.BlobEmitter;
import com.shatteredpixel.pixeldungeonunleashed.effects.Speck;
import com.shatteredpixel.pixeldungeonunleashed.items.Generator;
import com.shatteredpixel.pixeldungeonunleashed.items.Heap;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.artifacts.Artifact;
import com.shatteredpixel.pixeldungeonunleashed.items.potions.PotionOfHealing;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.Scroll;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.Weapon;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.enchantments.Ancient;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.enchantments.Glowing;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.enchantments.Holy;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.enchantments.Luck;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndMessage;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

import java.util.Arrays;

public class Donations extends Blob {
    protected int pos;

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );

        for (int i=0; i < LENGTH; i++) {
            if (cur[i] > 0) {
                pos = i;
                break;
            }
        }
    }

    @Override
    protected void evolve() {
        volume = off[pos] = cur[pos];

        if (Dungeon.visible[pos]) {
            Journal.add( Journal.Feature.ALTAR );
            if (Dungeon.difficultyLevel == Dungeon.DIFF_TUTOR && !Dungeon.tutorial_altar_seen) {
                Dungeon.tutorial_altar_seen = true;
                GameScene.show(new WndMessage("祭坛允许你捐赠祭品给地下城城主因为希望之" +
                    "神的干预。更昂贵的捐赠会带来更好的回报，包括一些难以得到的东西。 " +
                    "你能得到附魔武器、神器或者人物等级提升，你捐赠的祭品是累积的。"));
            }
        }
    }

    @Override
    public void seed( int cell, int amount ) {
        cur[pos] = 0;
        pos = cell;
        volume = cur[pos] = amount;
    }

    private static boolean throwItem(int cell, Item thrownItem) {
        // throw the item off of the Altar to avoid a redonation loop
        int newPlace;
        do {
            newPlace = cell + Level.NEIGHBOURS8[Random.Int(8)];
        } while (!Level.passable[newPlace] && !Level.avoid[newPlace] && Actor.findChar(newPlace) == null);
        Dungeon.level.drop(thrownItem, newPlace).sprite.drop(cell);

        return true;
    }

    public static void donate( int cell ) {
        Hero hero = Dungeon.hero;
        Heap heap = Dungeon.level.heaps.get( cell );

        if (heap != null && hero.donatedLoot != -1) {
            // don't allow accidental donations of important items such as keys or quest items
            // and don't insult the gods with insignificant donations
            if (heap.peek().unique || heap.peek().price() < 5 || (heap.peek() instanceof MissileWeapon)) {
                GLog.p("赫伯特拒绝了你的祭品");
                // throw the item off of the Altar to avoid a redonation loop

                throwItem(cell, heap.pickUp());
                return;
            }

            if (heap.peek() instanceof Scroll) {
                hero.donatedLoot += (heap.peek().price() * heap.peek().quantity() * 2);
                //GLog.p("Current donation value: " + Integer.toString(hero.donatedLoot));
            } else {
                hero.donatedLoot += (heap.peek().price() * heap.peek().quantity());
                //GLog.p("Current donation value: " + Integer.toString(hero.donatedLoot));
            }
            if (heap.peek().cursed) {
                // the Gods do not like to receive cursed goods and will punish the hero for this
                Buff.affect(hero, Burning.class).reignite(hero);
                Buff.affect(hero, Paralysis.class);
                Buff.prolong(hero, Vertigo.class, 5f);
                GLog.w("赫伯特对你的捐赠感到不满！");

                // the hero may not use an altar again during this run until amended
                hero.donatedLoot = -1;
            } else if (hero.donatedLoot >= 350) {
                // in order to get here you either have to donate a lot of goods all at once,
                // or you have been doing a lot of donations and collecting the lower rewards
                GLog.p("赫伯特很高兴，回赠了你！");
                if (Random.Int(3) == 0) {
                    GLog.p("赫伯特让你的大脑充斥着过去战斗的景象。");
                    hero.earnExp( hero.maxExp() );
                    hero.donatedLoot = 0;
                } else {
                    try {
                        GLog.p("你得到了一个神器！");
                        Item item = Generator.randomArtifact();
                        //if we're out of artifacts, return a ring instead.
                        if (item == null) {
                            GLog.p("哎呀...这里一个东西都没有...你得到了一个戒指!");
                            item : Generator.random(Generator.Category.RING);
                        }
                        GLog.p("你得到了: " + item.name());
                        throwItem(cell, item);
                        hero.donatedLoot = 0;
                    } catch (Exception ex) {
                        GLog.n(Arrays.toString(ex.getStackTrace()));
                    }
                }
            } else if (hero.donatedLoot >= 150) {
                if (Random.Int(3) == 0) {
                    // upgrade an item
                    Weapon wpn = (Weapon) Generator.random(Generator.Category.MELEE);
                    try {
                        switch (Random.Int(5)) {
                            case 0:
                                wpn.enchant(Glowing.class.newInstance());
                                break;
                            case 1:
                                wpn.enchant(Ancient.class.newInstance());
                                break;
                            case 2:
                                wpn.enchant(Holy.class.newInstance());
                                break;
                            case 3:
                                wpn.enchant(Luck.class.newInstance());
                                break;
                            case 4:
                                wpn.enchant();
                                break;
                        }
                        throwItem(cell, wpn);
                        hero.donatedLoot -= 150;
                        GLog.p("你得到了一个附魔的武器。");
                    } catch (NullPointerException e) {
                    } catch (InstantiationException e) {
                    } catch (IllegalAccessException e) {
                    }
                    GLog.p("你觉得你对赫伯特很亲近。");
                } else {
                    GLog.p("赫伯特对你微笑。");
                }
            } else if (hero.donatedLoot >= 50) {
                // some type of reward may be given to the hero, if so reduce the total donation value
                if (hero.HT < hero.HP) {
                    GLog.p("赫伯特治愈了你的伤口。");
                    PotionOfHealing.heal(hero);
                    hero.donatedLoot -= 40;
                } else if ((! hero.buffs().contains(Awareness.class)) && (Random.Int(6) == 0)) {
                    GLog.p("赫伯特揭示了你周围的秘密。");
                    Buff.affect(hero, Awareness.class, Awareness.DURATION * 2);
                    Dungeon.observe();
                    hero.donatedLoot -= 35;
                } else if ((! hero.buffs().contains(Bless.class)) && (Random.Int(6) == 0)){
                    GLog.p("赫伯特祝福了你。");
                    hero.belongings.uncurseEquipped();
                    Buff.affect(hero, Bless.class);
                    Buff.prolong(hero, Bless.class, 120f);
                    hero.donatedLoot -= 45;
                } else {
                    GLog.p("赫伯特似乎很高兴…也许今天是个是洗衣日…");
                }
            } else {
                GLog.p("赫伯特接受了你的捐赠…");
            }
            heap.donate();
        } else if (heap != null) {
            if ((heap.peek() instanceof Artifact || heap.peek().level >= 5)) {
                GLog.p("赫伯特很抱怨，但还是接受了你的捐赠并且想要你捐赠更多。");
                hero.donatedLoot = 0;
                heap.donate();
            } else {
                GLog.n("赫伯特怀恨在心。你现在需要捐赠一个非常强大的物品来取悦他…");
                throwItem(cell, heap.pickUp());
                return;
            }
        }
    }

    @Override
    public void use(BlobEmitter emitter) {
        super.use(emitter);
        emitter.start(Speck.factory(Speck.LIGHT), 0.2f, 0);
    }
}