package com.example.SpellCheckerNew.shared;

import java.io.Serializable;

public class EmptyFormatException extends Exception implements Serializable{
	
	public EmptyFormatException(){
		super("Dont fool me. Enter some text first!");
	}
	
	public EmptyFormatException(String message){
		super(message);
	}
}
