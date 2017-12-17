package com.example.SpellCheckerNew.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.example.SpellCheckerNew.client.GreetingService;
import com.example.SpellCheckerNew.shared.Dictionary;
import com.example.SpellCheckerNew.shared.EmptyFormatException;
import com.example.SpellCheckerNew.shared.FieldVerifier;
import com.example.SpellCheckerNew.shared.Suggestions;
import com.example.SpellCheckerNew.shared.Tokenizer;
import com.example.SpellCheckerNew.shared.UnknownWordsException;
import com.example.SpellCheckerNew.shared.Word;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public HashMap<Word, HashSet<Word>> greetServer(String input)
			throws IllegalArgumentException, UnknownWordsException, EmptyFormatException, IOException {
		Dictionary dict = new Dictionary(DictUtils.readDict("finalDict.txt"));
		Tokenizer t = new Tokenizer(dict, input);
		HashSet<Word> misspelled = t.getMisspelledWords();
		Suggestions suggestions = new Suggestions(dict, misspelled);
		HashMap<Word, HashSet<Word>> suggested = suggestions.getSuggestedWords();

		return suggested;

	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
