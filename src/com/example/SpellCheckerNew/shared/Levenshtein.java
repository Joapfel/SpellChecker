package com.example.SpellCheckerNew.shared;

import java.io.Serializable;

public class Levenshtein implements StringDistance, Serializable{

	int[][] table;
	
	public Levenshtein() {
		
	}
	/**
	 * gets the Levenshtein distance
	 * 
	 * @param s1
	 *            first Word
	 * @param s2
	 *            second Word
	 * @return Levenshtein distance
	 */
	public float getDistance(String s1, String s2) {
		int [][] distTable = table(s1, s2);
		return (float) distTable[s1.length()][s2.length()];
	}
	/**
	 * creates table for Levinsthein and applies the Algorithm.
	 * 
	 * @param w1
	 *            first Word
	 * @param w2
	 *            second Word
	 * @return returnValue
	 */
	private int[][] table(String w1, String w2){
		int[][] returnValue = new int[w1.length()+1][w2.length()+1];
		for(int row = 0; row < w1.length()+1; row++){
			returnValue[row][0] = row;
		}
		for(int column = 0; column < w2.length()+1; column++){
			returnValue[0][column] = column;
		}
		
		//levenshtein algorithm
		for(int row = 1; row < w1.length()+1; row++){
			for(int column = 1; column < w2.length()+1; column++){
				char c1 = w1.charAt(row-1);
				char c2 = w2.charAt(column-1);
				
				int aboveLeft = returnValue[row-1][column-1];
				int above = returnValue[row-1][column];
				int left = returnValue[row][column-1];
				int subCost = (c1==c2) ? 0 : 1;
				
				int min = Math.min(left + 1 , Math.min(above + 1, aboveLeft + subCost));
				returnValue[row][column] = min;
				
			}
		}
		
		return returnValue;
	}

}
