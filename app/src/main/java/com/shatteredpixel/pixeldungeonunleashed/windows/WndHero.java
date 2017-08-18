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

import java.util.Locale;

import com.shatteredpixel.pixeldungeonunleashed.actors.Actor;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.Mob;
import com.shatteredpixel.pixeldungeonunleashed.actors.mobs.pets.PET;
import com.shatteredpixel.pixeldungeonunleashed.effects.Speck;
import com.shatteredpixel.pixeldungeonunleashed.items.EquipableItem;
import com.shatteredpixel.pixeldungeonunleashed.items.Item;
import com.shatteredpixel.pixeldungeonunleashed.items.Torch;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.ClothArmor;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.MailArmor;
import com.shatteredpixel.pixeldungeonunleashed.items.armor.PlateArmor;
import com.shatteredpixel.pixeldungeonunleashed.items.food.ChargrilledMeat;
import com.shatteredpixel.pixeldungeonunleashed.items.food.Food;
import com.shatteredpixel.pixeldungeonunleashed.items.food.FrozenCarpaccio;
import com.shatteredpixel.pixeldungeonunleashed.items.food.MysteryMeat;
import com.shatteredpixel.pixeldungeonunleashed.items.food.Yumyuck;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.Scroll;
import com.shatteredpixel.pixeldungeonunleashed.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.pixeldungeonunleashed.items.wands.Wand;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Dagger;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Knuckles;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Longsword;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Quarterstaff;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.ShortSword;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.melee.Sword;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.CurareDart;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Dart;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.IncendiaryDart;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Javelin;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Shuriken;
import com.shatteredpixel.pixeldungeonunleashed.items.weapon.missiles.Tamahawk;
import com.shatteredpixel.pixeldungeonunleashed.levels.Level;
import com.shatteredpixel.pixeldungeonunleashed.plants.Fadeleaf;
import com.shatteredpixel.pixeldungeonunleashed.plants.Icecap;
import com.shatteredpixel.pixeldungeonunleashed.plants.Plant;
import com.shatteredpixel.pixeldungeonunleashed.plants.Sorrowmoss;
import com.shatteredpixel.pixeldungeonunleashed.plants.Stormvine;
import com.shatteredpixel.pixeldungeonunleashed.plants.YumyuckMoss;
import com.shatteredpixel.pixeldungeonunleashed.sprites.CharSprite;
import com.shatteredpixel.pixeldungeonunleashed.sprites.HeroSprite;
import com.shatteredpixel.pixeldungeonunleashed.ui.HealthBar;
import com.shatteredpixel.pixeldungeonunleashed.ui.Window;
import com.shatteredpixel.pixeldungeonunleashed.utils.GLog;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.shatteredpixel.pixeldungeonunleashed.Assets;
import com.shatteredpixel.pixeldungeonunleashed.Dungeon;
import com.shatteredpixel.pixeldungeonunleashed.Statistics;
import com.shatteredpixel.pixeldungeonunleashed.actors.buffs.Buff;
import com.shatteredpixel.pixeldungeonunleashed.actors.hero.Hero;
import com.shatteredpixel.pixeldungeonunleashed.scenes.GameScene;
import com.shatteredpixel.pixeldungeonunleashed.scenes.PixelScene;
import com.shatteredpixel.pixeldungeonunleashed.ui.BuffIndicator;
import com.shatteredpixel.pixeldungeonunleashed.ui.RedButton;
import com.shatteredpixel.pixeldungeonunleashed.utils.Utils;
import com.watabou.noosa.ui.Button;
import com.shatteredpixel.pixeldungeonunleashed.ui.NewRedButton;
import com.watabou.noosa.RenderedText;
import com.shatteredpixel.pixeldungeonunleashed.messages.Messages;

public class WndHero extends WndTabbed {

	private static final String TXT_STATS	= "属性";
	private static final String TXT_BUFFS	= "状态";
	private static final String TXT_PET = "宠物";

	private static final String TXT_HEALS = "%+dHP";

	private static final String TXT_EXP		= "经验";
	private static final String TXT_STR		= "力量";
	private static final String TXT_HEALTH	= "生命";
	private static final String TXT_GOLD	= "金币收集数";
	private static final String TXT_DEPTH	= "最深层数";
	private static final String TXT_KILLS = "击杀数";
	private static final String TXT_BREATH = "宠物能力";
	private static final String TXT_SPIN = "";
	private static final String TXT_STING = "蛰针";
	private static final String TXT_FEATHERS = "羽毛";
	private static final String TXT_SPARKLE = "法杖攻击";
	private static final String TXT_FANGS = "长牙";
	private static final String TXT_ATTACK = "攻击能力";
	private static final String TXT_PETS = "失去宠物数";

	
	private static final int WIDTH		= 100;
	private static final int TAB_WIDTH	= 40;
	
	private StatsTab stats;
	private PetTab pet;
	private BuffsTab buffs;
	
	private SmartTexture icons;
	private TextureFilm film;

	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}
		return null;
	}
	
	public WndHero() {
		
		super();
		
		icons = TextureCache.get( Assets.BUFFS_LARGE );
		film = new TextureFilm( icons, 16, 16 );
		
		stats = new StatsTab();
		add( stats );

		PET heropet = checkpet();

		if (heropet!=null){
			pet = new PetTab(heropet);
			add(pet);
		}
		
		buffs = new BuffsTab();
		add( buffs );
		
		add( new LabeledTab( TXT_STATS ) {
			protected void select( boolean value ) {
				super.select( value );
				stats.visible = stats.active = selected;
			};
		} );
		add( new LabeledTab( TXT_BUFFS ) {
			protected void select( boolean value ) {
				super.select( value );
				buffs.visible = buffs.active = selected;
			};
		} );
		if (heropet!=null){
			add(new LabeledTab(TXT_PET) {
				@Override
				protected void select(boolean value) {
					super.select(value);
					pet.visible = pet.active = selected;
				};
			});
		}

		resize( WIDTH, (int)Math.max( stats.height(), buffs.height() ) );

		layoutTabs();
		
		select( 0 );
	}
	
	private class StatsTab extends Group {

		private static final String TXT_TITLE		= " %d 级 %s %s";
		private static final String TXT_CATALOGUS	= "图鉴";
		private static final String TXT_JOURNAL		= "日志";
		
		private static final int GAP = 5;
		
		private float pos;
		
		public StatsTab() {
			
			Hero hero = Dungeon.hero;

			String TXT_DIFF;
			switch (Dungeon.difficultyLevel) {
				case Dungeon.DIFF_TUTOR:
					TXT_DIFF="教程";
					break;
				case Dungeon.DIFF_EASY:
					TXT_DIFF="简单";
					break;
				case Dungeon.DIFF_HARD:
					TXT_DIFF="困难";
					break;
				case Dungeon.DIFF_NTMARE:
					TXT_DIFF="噩梦";
					break;
				case Dungeon.DIFF_TEST:
					TXT_DIFF="测试";
					break;
				case Dungeon.DIFF_ENDLESS:
					TXT_DIFF="无尽";
					break;
				default:
					TXT_DIFF="普通";
					break;
			}


			IconTitle title = new IconTitle();
			title.icon( HeroSprite.avatar(hero.heroClass, hero.tier()) );
			title.label(Messages.get( TXT_TITLE, hero.lvl, hero.className(), TXT_DIFF ).toUpperCase( Locale.ENGLISH ), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect( 0, 0, WIDTH, 0 );
			add(title);

			NewRedButton btnCatalogus = new NewRedButton( TXT_CATALOGUS ) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndCatalogus() );
				}
			};
			btnCatalogus.setRect( 0, title.height(), btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2 );
			add( btnCatalogus );

			NewRedButton btnJournal = new NewRedButton( TXT_JOURNAL ) {
				@Override
				protected void onClick() {
					hide();
					GameScene.show( new WndJournal() );
				}
			};
			btnJournal.setRect(
				btnCatalogus.right() + 1, btnCatalogus.top(),
				btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2 );
			add( btnJournal );

			pos = btnCatalogus.bottom() + GAP;

			statSlot( TXT_STR, hero.STR() );
			statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT );
			statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp() );

			pos += GAP;

			statSlot( TXT_GOLD, Statistics.goldCollected );
			statSlot( TXT_DEPTH, Statistics.deepestFloor );

			pos += GAP;

			statSlot(TXT_PETS, Dungeon.hero.petCount);

			pos += GAP;
		}

		private void statSlot( String label, String value ) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);

			pos += GAP + txt.baseLine();
		}
		
		private void statSlot( String label, int value ) {
			statSlot( label, Integer.toString( value ) );
		}
		
		public float height() {
			return pos;
		}
	}

	private class BuffsTab extends Group {

		private static final int GAP = 2;

		private float pos;

		public BuffsTab() {
			for (Buff buff : Dungeon.hero.buffs()) {
				if (buff.icon() != BuffIndicator.NONE) {
					BuffSlot slot = new BuffSlot(buff);
					slot.setRect(0, pos, WIDTH, slot.icon.height());
					add(slot);
					pos += GAP + slot.height();
				}
			}
		}

		public float height() {
			return pos;
		}

		private class BuffSlot extends Button{

			private Buff buff;

			Image icon;
			RenderedText txt;

			public BuffSlot( Buff buff ){
				super();
				this.buff = buff;
				int index = buff.icon();

				icon = new Image( icons );
				icon.frame( film.get( index ) );
				icon.y = this.y;
				add( icon );

				RenderedText txt = PixelScene.renderText( buff.toString(), 8);
				txt.x = icon.width + GAP;
				txt.y = this.y + (int)(icon.height - txt.baseLine()) / 2;
				add( txt );

			}

			@Override
			protected void layout() {
				super.layout();
				icon.y = this.y;
				txt.x = icon.width + GAP;
				txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
			}

			@Override
			protected void onClick() {
				GameScene.show( new WndInfoBuff( buff ));
			}
		}
	}

	private class PetTab extends Group {

		private static final String TXT_TITLE = "%d 级 %s";
		private static final String TXT_FEED = "喂食";
		private static final String TXT_CALL = "呼唤";
		private static final String TXT_STAY = "停留";
		private static final String TXT_RELEASE = "解除";
		private static final String TXT_SELECT = "你想用什么喂你的宠物?";

		private CharSprite image;
		private RenderedText name;
		private HealthBar health;
		private BuffIndicator buffs;

		private static final int GAP = 5;

		private float pos;


		public PetTab(final PET heropet) {

			name = PixelScene.renderText(Utils.capitalize(heropet.name), 9 );
			name.hardlight(TITLE_COLOR);
			//add(name);

			image = heropet.sprite();
			add(image);

			health = new HealthBar();
			health.level((float) heropet.HP / heropet.HT);
			add(health);

			buffs = new BuffIndicator(heropet);
			add(buffs);



			IconTitle title = new IconTitle();
			title.icon(image);
			title.label(Messages.get(TXT_TITLE, heropet.level, heropet.name).toUpperCase(Locale.ENGLISH), 9);
			title.color(Window.SHPX_COLOR);
			title.setRect(0, 0, WIDTH, 0);
			add(title);

			NewRedButton btnFeed = new NewRedButton(TXT_FEED) {
				@Override
				protected void onClick() {
					hide();
					GameScene.selectItem(itemSelector, WndBag.Mode.ALL, TXT_SELECT);
				}
			};
			btnFeed.setRect(0, title.height(),
					btnFeed.reqWidth() + 2, btnFeed.reqHeight() + 2);
			add(btnFeed);

			NewRedButton btnCall = new NewRedButton(TXT_CALL) {
				@Override
				protected void onClick() {
					hide();
					heropet.callback = true;
					heropet.stay = false;
				}
			};
			btnCall.setRect(btnFeed.right() + 1, btnFeed.top(),
					btnCall.reqWidth() + 2, btnCall.reqHeight() + 2);
			add(btnCall);

			NewRedButton btnStay = new NewRedButton(heropet.stay ? TXT_RELEASE : TXT_STAY) {
				@Override
				protected void onClick() {
					hide();
					if (heropet.stay){
						heropet.stay = false;
					} else {
						heropet.stay = true;
					}
				}
			};
			btnStay.setRect(btnCall.right() + 1, btnCall.top(),
					btnStay.reqWidth() + 2, btnStay.reqHeight() + 2);

			add(btnStay);


			pos = btnStay.bottom() + GAP;

			statSlot(TXT_ATTACK, heropet.attackSkill(null));
			statSlot(TXT_HEALTH, heropet.HP + "/" + heropet.HT);
			statSlot(TXT_KILLS, heropet.kills);
			statSlot(TXT_EXP, heropet.level<20 ? heropet.experience + "/" + (heropet.level*(heropet.level+heropet.level)*3) : "Max");
			if (heropet.type==4 || heropet.type==5 || heropet.type==6 || heropet.type==7 || heropet.type==12){
				statSlot(TXT_BREATH, heropet.cooldown==0 ? "Ready" : heropet.cooldown + " Turns");
			} else if (heropet.type==1){
				statSlot(TXT_SPIN, heropet.cooldown==0 ? "Armed" : heropet.cooldown + " Turns");
			} else if (heropet.type==3){
				statSlot(TXT_FEATHERS, heropet.cooldown==0 ? "Ruffled" : heropet.cooldown + " Turns");
			} else if (heropet.type==8){
				statSlot(TXT_STING, heropet.cooldown==0 ? "Ready" : heropet.cooldown + " Turns");
			} else if (heropet.type==10 || heropet.type==11){
				statSlot(TXT_SPARKLE, heropet.cooldown==0 ? "Sparkling" : heropet.cooldown + " Turns");
			} else if (heropet.type==9){
				statSlot(TXT_FANGS, heropet.cooldown==0 ? "Fangs" : heropet.cooldown + " Turns");
			}

			pos += GAP;


		}

		private void statSlot(String label, String value) {

			RenderedText txt = PixelScene.renderText(label, 8);
			txt.y = pos;
			add(txt);

			txt = PixelScene.renderText(value, 8);
			txt.x = PixelScene.align(WIDTH * 0.65f);
			txt.y = pos;
			add(txt);
			
			pos += GAP + txt.baseLine();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				feed(item);
			}
		}
	};

	private boolean checkpetNear(){
		for (int n : Level.NEIGHBOURS8) {
			int c = Dungeon.hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}

	private void feed(Item item) {

		PET heropet = checkpet();
		boolean nomnom = checkFood(heropet.type, item);
		boolean nearby = checkpetNear();

		if (nomnom && nearby){
            // Items that stack return the price for the whole stack. Single items div by 1 so we are golden.
			int effect = item.price()/item.quantity();
			if (effect > 0){
                // Prevent overhealing.
                if (effect+heropet.HP >= heropet.HT) {
                    heropet.HP = heropet.HT;
                } else { heropet.HP+=effect;}
				heropet.sprite.emitter().burst(Speck.factory(Speck.HEALING),2);
				heropet.sprite.showStatus(CharSprite.POSITIVE, TXT_HEALS, effect);
				heropet.checkHP();
			}
			heropet.cooldown=1;
			if (item.isEquipped( Dungeon.hero )) {
				((EquipableItem)item).doUnequip( Dungeon.hero, true );
			}
			item.detach(Dungeon.hero.belongings.backpack);
			GLog.p("你的宠物吃了 %s.",item.name());
		}else if (!nearby){
			GLog.w("你的宠物跑远了!");
		} else {
			GLog.n("你的宠物拒绝了 %s.",item.name());


		}
	}

	private boolean checkFood(Integer petType, Item item){
		boolean nomnom = false;

		if (petType==1){ //Spidersilk klik
			if (item instanceof Food
					|| item instanceof ChargrilledMeat
					|| item instanceof FrozenCarpaccio
					|| item instanceof MysteryMeat
					|| item instanceof ClothArmor
					){
				nomnom=true;
			}
		}

		if (petType==2){ //steel bee
			if (item instanceof Food
					|| item instanceof ChargrilledMeat
					|| item instanceof FrozenCarpaccio
					|| item instanceof MysteryMeat
					){
				nomnom=true;
			}
		}
		if (petType==3){//Velocirooster
			if (item instanceof Plant.Seed
					|| item instanceof Yumyuck
					|| item instanceof YumyuckMoss.Seed
					){
				nomnom=true;
			}
		}
		if (petType==4){//red klik - fire
			if (item instanceof Torch
					|| item instanceof Plant.Seed
					|| item instanceof Wand
					|| item instanceof Scroll
					|| item instanceof IncendiaryDart
					|| item instanceof Tamahawk
					|| item instanceof Quarterstaff
					|| item instanceof ClothArmor
					){
				nomnom=true;
			}
		}

		if (petType==5){//phase klik - lit
			if (item instanceof Fadeleaf.Seed
					|| item instanceof FrozenCarpaccio
					|| item instanceof Stormvine.Seed
					|| item instanceof ScrollOfTeleportation
					|| item instanceof Icecap.Seed
					){
				nomnom=true;
			}
		}

		if (petType==6){//green klik - poison
			if (item instanceof CurareDart
					|| item instanceof MysteryMeat
					|| item instanceof Sorrowmoss.Seed
					|| item instanceof Yumyuck
					){
				nomnom=true;
			}
		}
		if (petType==7){//frost klik - ice
			if (item instanceof Food
					|| item instanceof FrozenCarpaccio
					|| item instanceof Icecap.Seed
					){
				nomnom=true;
			}
		}

		if (petType==8){ //scorpion
			if (item instanceof Food
					|| item instanceof ChargrilledMeat
					|| item instanceof FrozenCarpaccio
					|| item instanceof MysteryMeat
					){
				nomnom=true;
			}
		}

		if (petType==9){//Vorpal Bunny
			if (item instanceof Food
					|| item instanceof ChargrilledMeat
					|| item instanceof FrozenCarpaccio
					|| item instanceof MysteryMeat
					){
				nomnom=true;
			}
		}
		if (petType==10){//Fairy
			if (item instanceof Food
					|| item instanceof ChargrilledMeat
					|| item instanceof FrozenCarpaccio
					|| item instanceof MysteryMeat
					){
				nomnom=true;
			}
		}
		if (petType==11){//Sugarplum Fairy
			if (item instanceof Food
					|| item instanceof ChargrilledMeat
					|| item instanceof FrozenCarpaccio
					|| item instanceof MysteryMeat
					){
				nomnom=true;
			}
		}
		if (petType==12){//normal klik - metal
			if (item instanceof PlateArmor
					|| item instanceof MailArmor
					|| item instanceof Dart
					|| item instanceof Shuriken
					|| item instanceof Dagger
					|| item instanceof ShortSword
					|| item instanceof Sword
					|| item instanceof Longsword
					|| item instanceof Knuckles
					|| item instanceof Javelin
					){
				nomnom=true;
			}
		}
		return nomnom;
	}
}



