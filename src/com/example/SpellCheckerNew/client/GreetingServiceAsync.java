package com.example.SpellCheckerNew.client;

import java.util.HashMap;
import java.util.HashSet;

import com.example.SpellCheckerNew.shared.Word;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String name, AsyncCallback<HashMap<Word, HashSet<Word>>> callback);
}
