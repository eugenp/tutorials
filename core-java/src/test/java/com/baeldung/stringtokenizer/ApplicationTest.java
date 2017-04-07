package com.baeldung.stringtokenizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTest {

	Application application = new Application();
	List<String> expectedTokensForString = new ArrayList<String>();
	List<String> expectedTokensForFile = new ArrayList<String>();

	@Before
	public void init() {
		expectedTokensForString.add("Welcome");
		expectedTokensForString.add("to");
		expectedTokensForString.add("baeldung.com");

		expectedTokensForFile.add("1");
		expectedTokensForFile.add("IND");
		expectedTokensForFile.add("India");
		expectedTokensForFile.add("2");
		expectedTokensForFile.add("MY");
		expectedTokensForFile.add("Malaysia");
		expectedTokensForFile.add("3");
		expectedTokensForFile.add("AU");
		expectedTokensForFile.add("Australia");
	}

	@Test
	public void givenString_thenGetListOfString() {
		String str = "Welcome,to,baeldung.com";
		List<String> actualTokens = application.getTokens(str);
		assertEquals(expectedTokensForString, actualTokens);
	}

	@Test
	public void givenFile_thenGetListOfString() {
		List<String> actualTokens = application.getTokensFromFile("data.csv", "|");
		assertEquals(expectedTokensForFile, actualTokens);
	}

}
