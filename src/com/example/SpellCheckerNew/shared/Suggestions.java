package com.example.SpellCheckerNew.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * Suggestion class to display suggestions based on the misspelled word.
 */

public class Suggestions implements Serializable{

	private Dictionary dict;
	private HashSet<Word> misspelledWords;

	/**
	 * @param dict - dictionary with words
	 * @param misspelledWords - HashSet, where the misspelled words are saved.
	 */
	public Suggestions(Dictionary dict, HashSet<Word> misspelledWords) {
		super();
		this.dict = dict;
		this.misspelledWords = misspelledWords;
	}

	public Suggestions() {
		super();
	}

	/**
	 * Method to get the suggested words for the misspelled word.
	 * @return HashMap with the word and a HashSet with the suggestions.
	 */
	
	public HashMap<Word, HashSet<Word>> getSuggestedWords() {
		HashMap<Word, HashSet<Word>> returnValue = new HashMap<Word, HashSet<Word>>();

		for (Word mW : misspelledWords) {

			HashMap<Double, HashSet<Word>> distanceToWords = new HashMap<Double, HashSet<Word>>();
			HashSet<Word> suggestions = new HashSet<Word>(10);

			for (Word w : dict.getDict()) {
				if (mW.getContent().length() == w.getContent().length()
						|| mW.getContent().length() - 1 == w.getContent().length()
						|| mW.getContent().length() - 2 == w.getContent().length()
						|| mW.getContent().length() + 1 == w.getContent().length()
						|| mW.getContent().length() + 2 == w.getContent().length()) {

					
					Levenshtein levenshtein = new Levenshtein();
					double distance = levenshtein.getDistance(mW.getContent(), w.getContent());

					if (distanceToWords.containsKey(distance)) {
						distanceToWords.get(distance).add(w);
					} else {
						distanceToWords.put(distance, new HashSet<Word>());
						distanceToWords.get(distance).add(w);
					}
				}
			}

			Map<Double, HashSet<Word>> sorted = new TreeMap<Double, HashSet<Word>>(distanceToWords);

			for (Entry<Double, HashSet<Word>> entry : sorted.entrySet()) {
				for (Word w : entry.getValue()) {
					if (suggestions.size() < 10) {
						suggestions.add(w);
					}
				}
			}
			
			returnValue.put(mW, suggestions);
		}

		return returnValue;
	}

	public Dictionary getDict() {
		return dict;
	}

	public void setDict(Dictionary dict) {
		this.dict = dict;
	}

	public HashSet<Word> getMisspelledWords() {
		return misspelledWords;
	}

	public void setMisspelledWords(HashSet<Word> misspelledWords) {
		this.misspelledWords = misspelledWords;
	}

}
