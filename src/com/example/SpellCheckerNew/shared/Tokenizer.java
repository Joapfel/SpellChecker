package com.example.SpellCheckerNew.shared;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.HashSet;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Tokenizer implements Serializable{
	
	private Dictionary dict;
	String userInput = "";
	
	public Tokenizer(Dictionary dict, String userInput){
		super();
		this.dict = dict;
		this.userInput = userInput;
	}
	/**
	 * gets the mispelled words
	 * @return returnValue set of misspelled words
	 * @throws IOException
	 * @throws UnknownWordsException
	 * @throws EmptyFormatException
	 */
	public HashSet<Word> getMisspelledWords() throws IOException, UnknownWordsException, EmptyFormatException{
		//Set of misspelled words
		HashSet<Word> returnValue = new HashSet<Word>();
		
		//Set of correct spelled words
		HashSet<Word> correctWords = new HashSet<Word>();
		
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<CoreLabel>(new StringReader(userInput), new CoreLabelTokenFactory(), "");
		while(ptbt.hasNext()){
			CoreLabel label = ptbt.next();
			int wordBeginPosition = label.beginPosition();
			int wordEndPosition = label.endPosition();
			
			if(isPunctuation(label.toString())) continue;
			
			Word word = new Word(label.toString().toLowerCase());
			word.setContentBeginPosition(wordBeginPosition);
			word.setContentEndPosition(wordEndPosition);
			
			if(!isInDict(word)){
				returnValue.add(word);
			}else{
				correctWords.add(word);
			}
		}
		returnValue = deleteBrackets(returnValue);
		
		if(correctWords.isEmpty() && returnValue.isEmpty()) throw new EmptyFormatException();
		int percentageOfWrongWords = checkPercentageOfWrongWords(correctWords, returnValue);
		if (percentageOfWrongWords > 50) throw new UnknownWordsException();
		
		return returnValue;
	}
	/**
	 * If is in Dict
	 * @param word
	 * @return
	 */
	private boolean isInDict(Word word){
		return dict.getDictAsStrings().contains(word.getContent());
	}
	/**
	 * If is punctuation
	 * @param p
	 * @return
	 */
	private boolean isPunctuation(String p){
		return  p.equals("''") || p.equals("``") || p.equals("��") ||
			p.matches("\\d+") || p.matches("\\W") || p.equals("--");
	}
	/**
	 * Delete Brackets
	 * @param words
	 * @return
	 */
	private HashSet<Word> deleteBrackets(HashSet<Word> words){
		HashSet<Word> returnValue = new HashSet<Word>();
		for(Word w : words){
			// ( ) brackets
			if(!(w.getContent().equals("-lrb-") || w.getContent().equals("-rrb-")
					// [ ] brackets
					|| w.getContent().equals("-lsb-") || w.getContent().equals("-rsb-")
					// { } brackets
					|| w.getContent().equals("-lcb-") || w.getContent().equals("-rcb-"))){
				
				returnValue.add(w);
			}
		}
		return returnValue;
	}
	/**
	 * Check percentage of misspelled words
	 * @param correctWords
	 * @param misspelledWords
	 * @return
	 */
	private int checkPercentageOfWrongWords(HashSet<Word> correctWords, HashSet<Word> misspelledWords){
		int numberOfCorrectWords = correctWords.size();
		int numberOfMisspelledWords = misspelledWords.size();
		int allWords = numberOfCorrectWords + numberOfMisspelledWords;
		int percentageOfMisspelledWords = (numberOfMisspelledWords / allWords) * 100;
		
		return percentageOfMisspelledWords;
	}
}
