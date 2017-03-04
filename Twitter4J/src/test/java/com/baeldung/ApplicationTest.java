package com.baeldung;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import twitter4j.TwitterException;

public class ApplicationTest {

	@Test
	public void givenText_updateStatus() throws TwitterException {
		String status = "baeldung rocks";
		String text = Application.createTweet(status);
		assertEquals(status,text);
	}
	
}
