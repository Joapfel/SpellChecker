package com.example.SpellCheckerNew.shared;

import java.io.Serializable;
import java.util.HashSet;

public class Dictionary implements Serializable{

	private HashSet<Word> dict;
	
	public Dictionary() {
		super();
	}
	/**
	 * Dictionary as Hashset of words called dict.
	 * 
	 * @param dict
	 */
	public Dictionary(HashSet<Word> dict) {
		super();
		this.dict = dict;
	}
	/**
	 * gets Dict.
	 * 
	 * @return dict
	 */
	public HashSet<Word> getDict() {
		return dict;
	}
	/**
	 * gets Dict as Strings.
	 * 
	 * @return returnValue Dict as String
	 */
	public HashSet<String> getDictAsStrings(){
		HashSet<String> returnValue = new HashSet<>();
		for(Word w : dict){
			returnValue.add(w.getContent());
		}
		return returnValue;
	}
	/**
	 * sets Dict.
	 * 
	 * @param dict
	 */
	public void setDict(HashSet<Word> dict) {
		this.dict = dict;
	}
	
}
