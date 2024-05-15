package com.baeldung.htmlunit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitAndJUnitLiveTest {

    private WebClient webClient;

    @Before
    public void init() throws Exception {
        webClient = new WebClient();
    }

    @After
    public void close() throws Exception {
        webClient.close();
    }

    @Test
    public void givenAClient_whenEnteringBaeldung_thenPageTitleIsOk() throws Exception {
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webClient.getPage("http://www.baeldung.com/");
        Assert.assertEquals("Baeldung | Java, Spring and Web Development tutorials", page.getTitleText());
    }

}
