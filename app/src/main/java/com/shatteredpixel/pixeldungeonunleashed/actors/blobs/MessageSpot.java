package com.shatteredpixel.pixeldungeonunleashed.actors.blobs;

import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.shatteredpixel.pixeldungeonunleashed.windows.WndMessage;
import com.watabou.utils.Bundle;

// This class didn't work out the way I wanted and I moved in another direction... but since I don't
// want to risk breaking previous saved-tutorial level games I am leaving this in for several builds.
// remove this after 1Feb2016, don't use at all.

public class MessageSpot extends Blob {
    public enum myMessage { NONE, FIRST_SIGN, FIRST_EXIT, FIRST_MOB, TACTICS_TIP, FIRST_FOOD, FIRST_TRAP,
        FIRST_LOCKED, FIRST_BOSS, GARDEN_ROOM, ALTAR_ROOM, WELL_TRANSMUTE, WELL_HEALTH, WELL_AWARENESS }

    public myMessage messageType = myMessage.NONE;
    protected int pos;  // this blobs position on the dungeon map

    private static final String myMsg		= "myMsg";

    private static int first_trap = 0;

    @Override
    protected void evolve() {
        volume = off[pos] = cur[pos];
        GLog.i("blob " + messageType + " active, volume = " + volume);
        if (Dungeon.visible[pos]) {
            switch (messageType) {
                case NONE:
                    // this message blob has been seen by the player already
                    break;
                case FIRST_EXIT:
                    GameScene.show(new WndMessage("在你探索完这层之前，你已经找到了下楼的楼梯！" +
                        "你可以用这些楼梯继续你的冒险。"));
                    break;
                case FIRST_TRAP:
                    if (first_trap == 0) {
                        Dungeon.level.discover(pos);
                        GameScene.show(new WndMessage("这是一个陷阱。大多数陷阱都是隐藏的，直到你发现它们。当 " +
                                " 你踩到陷阱时，它们会对你做一些不好的事。但就像这个游戏中的所有障碍一样，通常有" +
                                "一个巧妙的方法来绕过他们, 例如漂浮在陷阱上面或者把物品扔到 " +
                                "陷阱里 。"));
                        first_trap = 1;
                    }
                    break;
                case FIRST_SIGN:
                case FIRST_LOCKED:
                case FIRST_MOB:
                case TACTICS_TIP:
                case FIRST_FOOD:
                case FIRST_BOSS:
                case GARDEN_ROOM:
                case ALTAR_ROOM:
                case WELL_TRANSMUTE:
                case WELL_AWARENESS:
                case WELL_HEALTH:
                default:
                    break;
            }
            messageType = myMessage.NONE;
            super.clear(pos);
        }
    }

    @Override
    public void seed( int cell, int amount ) {
        GLog.i("Blob Placed at " + cell);
        cur[pos] = 0;
        pos = cell;
        volume = cur[pos] = amount;
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle(bundle);
        bundle.put(myMsg, messageType);
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        bundle.getInt(myMsg);

        for (int i=0; i < LENGTH; i++) {
            if (cur[i] > 0) {
                pos = i;
                break;
            }
        }
    }
}
