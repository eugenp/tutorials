package com.baeldung.htmlunit;

import org.junit.Assert;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitTest {

    @Test
    public void givenAClient_whenEnteringBaeldung_thenPageTitleIsCorrect() throws Exception {
        try (final WebClient webClient = new WebClient()) {
            webClient.getOptions().setThrowExceptionOnScriptError(false);

            final HtmlPage page = webClient.getPage("http://www.baeldung.com/");
            Assert.assertEquals("Baeldung | Java, Spring and Web Development tutorials", page.getTitleText());
        }
    }

}
