package com.baeldung.stringtokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Application {

	public List<String> getTokens(String str) {
		List<String> tokens = new ArrayList<String>();
<<<<<<< HEAD
		// StringTokenizer tokenizer = new StringTokenizer( str );
		StringTokenizer tokenizer = new StringTokenizer(str, ",");
		// StringTokenizer tokenizer = new StringTokenizer( str , "," , true );
		while (tokenizer.hasMoreElements()) {
			tokens.add(tokenizer.nextToken());
			// tokens.add( tokenizer.nextToken("e") );
=======
//  StringTokenizer tokenizer = new StringTokenizer( str );
		StringTokenizer tokenizer = new StringTokenizer( str , "," );
//	StringTokenizer tokenizer = new StringTokenizer( str , "," , true );
		while (tokenizer.hasMoreElements()) {
			tokens.add( tokenizer.nextToken() );
//		tokens.add( tokenizer.nextToken("e") );
>>>>>>> 85e183ffba7826f71fd6c7d2557318bfc7484e34
		}
		int tokenLength = tokens.size();
		return tokens;
	}
<<<<<<< HEAD

	public List<String> getTokensFromFile(String path, String delim) {
		List<String> tokens = new ArrayList<String>();
		String currLine = "";
		StringTokenizer tokenizer;
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Application.class.getResourceAsStream("/" + path)))) {
			while ((currLine = br.readLine()) != null) {
				tokenizer = new StringTokenizer(currLine, delim);
				while (tokenizer.hasMoreElements()) {
					tokens.add(tokenizer.nextToken());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tokens;
	}
	

=======
  
>>>>>>> 85e183ffba7826f71fd6c7d2557318bfc7484e34
}
