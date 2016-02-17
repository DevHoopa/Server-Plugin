package de.flaflo.language;

import java.util.HashMap;

import de.flaflo.language.LanguageManager.Dictionary;

public abstract class Language {

	private String language;
	private HashMap<String, String> dict;
	
	public Language(String lang) {
		language = lang;
		dict = new HashMap<String, String>();
		
		setup();
	}
	
	public abstract void setup();
	
	public String getString(String dict) {
		return this.dict.get(dict);
	}
	
	public String getString(Dictionary dict) {
		return this.dict.get(dict.toString());
	}
	
	public void addString(String dict, String str) {
		this.dict.put(dict, str);
	}
	
	public void addString(Dictionary dict, String str) {
		this.dict.put(dict.toString(), str);
	}
	
	public String getName() {
		return language;
	}
}
