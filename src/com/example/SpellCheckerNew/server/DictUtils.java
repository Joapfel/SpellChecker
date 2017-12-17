package com.example.SpellCheckerNew.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.rmi.MarshalException;
import java.util.HashMap;
import java.util.HashSet;

import com.example.SpellCheckerNew.shared.Word;
import com.google.gwt.dev.shell.remoteui.MessageTransport.RequestException;
import com.google.gwt.dev.shell.remoteui.RemoteMessageProto.Message.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

public class DictUtils implements Serializable {

	public static HashSet<Word> readDictGWT(String path) throws IOException{
		HashSet<Word> returnValue = new HashSet<Word>();
		
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, path);
		try {
			requestBuilder.sendRequest("", new RequestCallback() {
				
				@Override
				public void onResponseReceived(com.google.gwt.http.client.Request request, Response response) {
					// TODO Auto-generated method stub
					String text = response.getText();
					String[] allWord = text.split("\\s+");
					for(String w : allWord){
						if(w.trim().equals("")) continue;
						
						Word word = new Word(w);
						returnValue.add(word);
					}
				}
				
				@Override
				public void onError(com.google.gwt.http.client.Request request, Throwable exception) {
					// TODO Auto-generated method stub
					Window.alert(exception.getMessage());
				}
			});
		} catch (com.google.gwt.http.client.RequestException e) {
			throw new IOException("Requestbuilder has a problem...");
		}
		return returnValue;
	}
	
	/**
	 * read the Dictionary.
	 * 
	 * @param path
	 * @return returnValue returns the dict as HashSet of Words
	 * @throws IOException
	 */	
	public static HashSet<Word> readDict(String path) throws IOException{
		HashSet<Word> returnValue = new HashSet<Word>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		String line = "";
		while ((line = br.readLine()) != null) {
			if(line.trim().equals("")) continue;
			
			String singleWord = line;
			Word word = new Word(singleWord);
			returnValue.add(word);
		}
		
		br.close();
		return returnValue;
	}
	
	/**
	 * reads DictMap.
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static HashMap<Character, HashSet<Word>> readDictMap(String path) throws IOException{
		HashMap<Character, HashSet<Word>> returnValue = new HashMap<Character, HashSet<Word>>();

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		String line = "";
		while ((line = br.readLine()) != null) {
			if(line.trim().equals("")) continue;
			
			String singleWord = line;
			Word word = new Word(singleWord);
			Character first = word.getContent().charAt(0);
			if(returnValue.containsKey(first)){
				returnValue.get(first).add(word);
			}else{
				returnValue.put(first, new HashSet<Word>());
				returnValue.get(first).add(word);
			}
		}
		
		br.close();
		return returnValue;
	}
	/**
	 * Read the Dictionary by word length.
	 * 
	 * @param path
	 * @return a HashMap with the length of the words a key 
	 * @throws IOException
	 */
	public static HashMap<Integer, HashSet<Word>> readDictByLength(String path) throws IOException{
		HashMap<Integer, HashSet<Word>> returnValue = new HashMap<Integer, HashSet<Word>>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path))));
		String line = "";
		while ((line = br.readLine()) != null) {
			if(line.trim().equals("")) continue;
			
			String singleWord = line;
			Word word = new Word(singleWord);
			int wordLength = singleWord.length();
			if(returnValue.containsKey(wordLength)){
				returnValue.get(wordLength).add(word);
			}else{
				returnValue.put(wordLength, new HashSet<Word>());
				returnValue.get(wordLength).add(word);
			}
		}
		
		br.close();
		return returnValue;
	}
}
