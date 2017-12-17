package com.example.SpellCheckerNew.shared;

import java.io.Serializable;

public class UnknownWordsException extends Exception implements Serializable{
	
	public UnknownWordsException(){
		super("This text contains to many unknown words.");
	}
	public UnknownWordsException(String message){
		super(message);
	}

}
