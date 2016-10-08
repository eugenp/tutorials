package com.baeldung.htmlunit;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.context.WebApplicationContext;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestConfig.class })
public class HtmlUnitAndSpringTest {

	@Autowired
	private WebApplicationContext wac;

	private WebClient webClient;

	@Before
	public void setup() {
		webClient = MockMvcWebClientBuilder.webAppContextSetup(wac).build();
	}

	@Test
	@Ignore
	public void givenAMessage_whenSent_thenItShows() {
		final String text = "Hello world!";
		HtmlPage page;

		try {

			page = webClient.getPage("http://localhost/message/showForm");
			System.out.println(page.asXml());

			final HtmlTextInput messageText = page.getHtmlElementById("message");
			messageText.setValueAttribute(text);

			final HtmlForm form = page.getForms().get(0);
			final HtmlSubmitInput submit = form.getOneHtmlElementByAttribute("input", "type", "submit");
			final HtmlPage newPage = submit.click();

			final String receivedText = newPage.getHtmlElementById("received").getTextContent();

			Assert.assertEquals(receivedText, text);
			System.out.println(newPage.asXml());

		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void givenAClient_whenEnteringBaeldung_thenPageTitleIsCorrect() throws Exception {
		try (final WebClient client = new WebClient()) {

			webClient.getOptions().setThrowExceptionOnScriptError(false);

			final HtmlPage page = webClient.getPage("http://www.baeldung.com/");
			Assert.assertEquals("Baeldung | Java, Spring and Web Development tutorials", page.getTitleText());
		}
	}

}
