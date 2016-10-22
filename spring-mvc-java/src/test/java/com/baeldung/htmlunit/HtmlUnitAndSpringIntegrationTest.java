package com.baeldung.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;

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
public class HtmlUnitAndSpringIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private WebClient webClient;

    @Before
    public void setup() {
        webClient = MockMvcWebClientBuilder.webAppContextSetup(wac).build();
    }

    //

    @Test
    @Ignore("Related view message.html does not exist check MessageController")
    public void givenAMessage_whenSent_thenItShows() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        final String text = "Hello world!";
        final HtmlPage page = webClient.getPage("http://localhost/message/showForm");
        System.out.println(page.asXml());

        final HtmlTextInput messageText = page.getHtmlElementById("message");
        messageText.setValueAttribute(text);

        final HtmlForm form = page.getForms().get(0);
        final HtmlSubmitInput submit = form.getOneHtmlElementByAttribute("input", "type", "submit");
        final HtmlPage newPage = submit.click();

        final String receivedText = newPage.getHtmlElementById("received").getTextContent();

        Assert.assertEquals(receivedText, text);
        System.out.println(newPage.asXml());
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
