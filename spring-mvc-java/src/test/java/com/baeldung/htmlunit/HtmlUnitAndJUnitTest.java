package com.baeldung.htmlunit;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitAndJUnitTest {

	@Before
	public void init() throws Exception {
	    webClient = new WebClient();
	}

	@After
	public void close() throws Exception {
	    webClient.close();
	}

	@Test
	public void givenAClient_whenEnteringBaeldung_thenPageTitleIsOk()
	  throws Exception {
	    webClient.getOptions().setThrowExceptionOnScriptError(false);
	    HtmlPage page = webClient.getPage("http://www.baeldung.com/");
	    Assert.assertEquals(
	      "Baeldung | Java, Spring and Web Development tutorials",
		page.getTitleText());
	}

}
