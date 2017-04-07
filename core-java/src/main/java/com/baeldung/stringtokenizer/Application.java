package com.baeldung.stringtokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Application {

	public List<String> getTokens(String str) {
		List<String> tokens = new ArrayList<String>();
//		StringTokenizer tokenizer = new StringTokenizer( str );
		StringTokenizer tokenizer = new StringTokenizer( str , "," );
//		StringTokenizer tokenizer = new StringTokenizer( str , "," , true );
		while (tokenizer.hasMoreElements()) {
			tokens.add( tokenizer.nextToken() );
//			tokens.add( tokenizer.nextToken( "," ) );
		}
		return tokens;
	}

}
