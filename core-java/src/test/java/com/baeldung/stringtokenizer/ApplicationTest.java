package com.baeldung.stringtokenizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

	Application application = new Application();
	List<String> expectedTokens = new ArrayList<String>();
	
	@Before
	public void init() {
		expectedTokens.add( "Welcome" );
		expectedTokens.add( "to" );
		expectedTokens.add( "baeldung.com" );
	}
	
	@Test
	public void givenString_thenGetListOfString() {
		String str = "Welcome,to,baeldung.com";
		List<String> actualTokens = application.getTokens(str);
		assertEquals(expectedTokens, actualTokens);
	}
	
}
