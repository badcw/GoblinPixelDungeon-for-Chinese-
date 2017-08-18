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
package com.shatteredpixel.pixeldungeonunleashed.windows;

import com.shatteredpixel.pixeldungeonunleashed.GoblinsPixelDungeon;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;
import com.shatteredpixel.pixeldungeonunleashed.ui.RenderedTextMultiline;
import com.shatteredpixel.pixeldungeonunleashed.Chrome;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.scenes.PixelScene;
import com.shatteredpixel.pixeldungeonunleashed.ui.Window;
import com.watabou.utils.SparseArray;

public class WndStory extends Window {

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 144;
	private static final int MARGIN = 6;
	
	private static final float bgR	= 0.77f;
	private static final float bgG	= 0.73f;
	private static final float bgB	= 0.62f;
	
	public static final int ID_SEWERS		= 0;
	public static final int ID_PRISON		= 1;
	public static final int ID_CAVES		= 2;
	public static final int ID_METROPOLIS	= 3;
	public static final int ID_FROZEN       = 4;
	public static final int ID_HALLS		= 5;
	public static final int ID_TUTOR_1      = 6;
	public static final int ID_TUTOR_2      = 7;
	public static final int ID_SPECIAL_1    = 8;

	private static final SparseArray<String> CHAPTERS = new SparseArray<String>();

	static {
		CHAPTERS.put( ID_SEWERS,
				"这片地牢位于 Brassmoon 市正下方,它的最上层其实是由城市的下水道系统组成的。\n\n " +
						"由于下方不断渗透的黑暗能量,这些本应无害的下水道生物变得越来越" +
						"危险.城市向这里派出了巡逻队并试图保护其上方的区域.但 " +
						"他们的影响也在逐渐式微。" );

		CHAPTERS.put( ID_PRISON,
				"很多年以后一个关押最危险怪物的地下监狱建在了这里.在当时它看起来 " +
						"像一个非常好的主意,因为这个地方真的很难逃脱.但很快黑暗的瘴气开始" +
						"从下方弥漫,使得囚徒和卫兵变得疯狂.最后监狱被遗弃了,尽管有些囚徒" +
						"被留在这里了。" );

		CHAPTERS.put( ID_CAVES,
				"这些伸展到遗弃监狱下方的洞穴几乎没人居住。它们在地下太深难以被城市利用 " +
						"，缺乏各种矮人感兴趣的矿物。过去这里有一个贸易站建在 " +
						"在两地之间， 但它自从矮人王国Greyhill的衰退已经消失。 " +
						"只有无处不在的豺狼人和地下生物居住在这里。" );

		CHAPTERS.put( ID_METROPOLIS,
				"Greyhill曾经是最伟大的矮人城邦， 在其鼎盛时期矮人的机械化部队" +
						"成功的击退了古神及其恶魔军队的入侵。但据说 归来的骑士 " +
						"给他带来了腐败的种子，所以 这场胜利是这个地下王国灭亡的开始。" );

		CHAPTERS.put( ID_FROZEN,
				"空气突然变冷，这里是矮人王国的延伸。但多年的荒废已经使一些东西 "+
						"接管了。 随着你身后的不死矮人国王、, 是时候奋力推进了、。 充满这里的魔法  "+
						"感觉像是恶魔的。\n\n");
		CHAPTERS.put( ID_HALLS,
				"在过去这些楼层是矮人王国的郊区。与古神的战争中获得高额代价的胜利后， " +
						"矮人族虚弱而不能清除剩下的恶魔。渐渐地恶魔使这个地方变成了地狱的延伸。 " +
						"而且现在它被称为恶魔大厅。\n\n" +
						"非常少的冒险者能如此深入..." );

		CHAPTERS.put (ID_TUTOR_1,
				"欢迎来到哥布林的像素地牢! 你现在处于教学难度; 你可以在游戏外设置菜单里" +
						"改变难度 (它的形状像小齿轮). 游戏内的标志牌包含有当前难度的有用的信息,它们 " +
						"值得一看。你也可以存档 (只在在普通难度里,简单难度你可以随地保存 )，只要你站在标志牌的旁边。 " +
						"按下游戏设置按钮(右上角)。\n\n" +
						"现在你需要寻找下楼的楼梯。");

		CHAPTERS.put (ID_TUTOR_2,
				"这是你第一个boss关。 在这一层你会看到前往下一层的门被锁住了,你得  " +
						"在下去之前找到楼层某个地方的特殊boss钥匙。");

		CHAPTERS.put (ID_SPECIAL_1,
				"城市Brassmoon上面被冰雪覆盖 , 霜已经降到了地牢的第一层。 "+
						"地牢的上层部分构成了城市的下水道系统。\n\n " +
						"由于下方不断渗透的黑暗能量这些本应无害的下水道生物变得越来越" +
						"危险。 城市向这里派出巡逻队并试图保护其上方的区域， 但 " +
						"他们慢慢地失败了。\n\n 找个地方足够危险了, 但至少这里的邪恶魔法还不强大。");
	};
	
	private RenderedTextMultiline tf;
	
	private float delay;
	
	public WndStory( String text ) {
		super( 0, 0, Chrome.get( Chrome.Type.SCROLL ) );
		
		tf = PixelScene.renderMultiline( text, 7 );
		tf.maxWidth(GoblinsPixelDungeon.landscape() ?
				WIDTH_L - MARGIN * 2:
				WIDTH_P - MARGIN *2);
		tf.invert();
		tf.setPos(MARGIN, 0);
		add( tf );
		
		add( new TouchArea( chrome ) {
			@Override
			protected void onClick( Touch touch ) {
				hide();
			}
		} );
		
		resize( (int)(tf.width() + MARGIN * 2), (int)Math.min( tf.height(), 180 ) );
	}
	
	@Override
	public void update() {
		super.update();
		
		if (delay > 0 && (delay -= Game.elapsed) <= 0) {
			shadow.visible = chrome.visible = tf.visible = true;
		}
	}
	
	public static void showChapter( int id ) {
		
		if (Dungeon.chapters.contains( id )) {
			return;
		}
		
		String text = CHAPTERS.get( id );
		if (text != null) {
			WndStory wnd = new WndStory( text );
			if ((wnd.delay = 0.6f) > 0) {
				wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
			}
			
			Game.scene().add( wnd );
			
			Dungeon.chapters.add( id );
		}
	}


	public static void showChapter(String text) {
		if (text != null) {
			WndStory wnd = new WndStory( text );
			if ((wnd.delay = 0.6f) > 0) {
				wnd.chrome.visible = wnd.tf.visible = false;
			}

			Game.scene().add( wnd );
		}
	}
}
