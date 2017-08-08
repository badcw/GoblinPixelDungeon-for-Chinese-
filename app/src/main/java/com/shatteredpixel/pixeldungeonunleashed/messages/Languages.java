/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 */
package com.shatteredpixel.pixeldungeonunleashed.messages;

import java.util.Locale;

public enum Languages {
	ENGLISH("english",      "", Status.REVIEWED, null, null),

	//RUSSIAN("русский",      "ru", Status.REVIEWED, new String[]{"ConsideredHamster", "Inevielle", "yarikonline"}, new String[]{"AttHawk46", "HerrGotlieb", "Shamahan", "Un_logic"}),
	//KOREAN("한국어",         "ko", Status.REVIEWED, new String[]{"Flameblast12"}, new String[]{"Ddojin0115", "Eeeei", "Lsiebnie", "Linterpreteur", "WondarRabb1t"}),
	CHINESE("中文",          "zh", Status.REVIEWED, new String[]{"......"},null);
	//FINNISH("suomi", 		"fi", Status.REVIEWED, new String[]{"TenguTheKnight"}, null ),

	//GERMAN("deutsch",       "de", Status.UNREVIEWED, new String[]{"Dallukas", "KrystalCroft", "Wuzzy", "Zap0", "Davedude" }, new String[]{"DarkPixel", "ErichME", "Sarius", "Sorpl3x", "ThunfischGott", "Oragothen"}),
	//FRENCH("français",      "fr", Status.UNREVIEWED, new String[]{"Emether", "Canc42", "Kultissim", "Minikrob"}, new String[]{"Alsydis", "Basttee", "Draal", "Go11um", "Solthaar"}),
	//ITALIAN("italiano",		"it", Status.UNREVIEWED, new String[]{"Bizzolino", "Funnydwarf"}, new String[]{"4est", "Andrearubbino00", "DaniMare", "Danzl", "Nessunluogo", "Umby000"}),
	//HUNGARIAN("magyar",     "hu", Status.UNREVIEWED, new String[]{"Dorheim"}, new String[]{"Clarovani", "Dhialub", "Nanometer", "Nardomaa", "Navetelen"}),

	//SPANISH("español",      "es", Status.INCOMPLETE, new String[]{"Kiroto", "Grayscales"}, new String[]{"Alesxanderk", "CorvosUtopy", "Dewstend", "Dyrran", "Fervoreking", "Illyatwo2", "Kohru", "Alfongad", "Ctrijueque", "Dhg121", "Jonismack1"}),
	//PORTUGUESE("português", "pt", Status.INCOMPLETE, new String[]{"Matheus208"}, new String[]{"ChainedFreaK", "JST", "TDF2001", "Try31"}),
	//POLISH("polski",        "pl", Status.INCOMPLETE, null, new String[]{"Darden", "Deksippos", "MJedi", "Scharnvirk", "Shmilly", "Dusakus", "Kuadziw", "Michaub", "Ozziezombie", "Szymex73"});

	public enum Status{
		//below 60% complete languages are not added.
		INCOMPLETE, //60-99% complete
		UNREVIEWED, //100% complete
		REVIEWED    //100% reviewed
	}

	private String name;
	private String code;
	private Status status;
	private String[] reviewers;
	private String[] translators;

	Languages(String name, String code, Status status, String[] reviewers, String[] translators){
		this.name = name;
		this.code = code;
		this.status = status;
		this.reviewers = reviewers;
		this.translators = translators;
	}

	public String nativeName(){
		return name;
	}

	public String code(){
		return code;
	}

	public Status status(){
		return status;
	}

	public String[] reviewers() {
		if (reviewers == null) return new String[]{};
		else return reviewers.clone();
	}

	public String[] translators() {
		if (translators == null) return new String[]{};
		else return translators.clone();
	}

	public static Languages matchLocale(Locale locale){
		return matchCode(locale.getLanguage());
	}

	public static Languages matchCode(String code){
		for (Languages lang : Languages.values()){
			if (lang.code().equals(code))
				return lang;
		}
		return ENGLISH;
	}

}