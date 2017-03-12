package com.baeldung;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ratpack.test.MainClassApplicationUnderTest;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class ApplicationTest {

	MainClassApplicationUnderTest appUnderTest = new MainClassApplicationUnderTest(Application.class);

	@Test
	public void givenDefaultUrl_getStaticText() {
		assertEquals("Welcome to baeldung ratpack!!!", appUnderTest.getHttpClient().getText("/"));
	}
	
	@Test
	public void givenDynamicUrl_getDynamicText() {
		assertEquals("Hello dummybot!!!", appUnderTest.getHttpClient().getText("/dummybot"));
	}
	
	@After
	public void shutdown() {
		appUnderTest.close();
	}

}