package com.example.SpellCheckerNew.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import com.example.SpellCheckerNew.shared.EmptyFormatException;
import com.example.SpellCheckerNew.shared.UnknownWordsException;
import com.example.SpellCheckerNew.shared.Word;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	HashMap<Word, HashSet<Word>> greetServer(String name) throws IllegalArgumentException, IOException, UnknownWordsException, EmptyFormatException;
}
