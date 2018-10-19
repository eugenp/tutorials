package com.baeldung.htmlunit;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitWebScrapingLiveTest {

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
    public void givenBaeldungArchive_whenRetrievingArticle_thenHasH1() throws Exception {
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        final String url = "http://www.baeldung.com/full_archive";
        final HtmlPage page = webClient.getPage(url);
        final String xpath = "(//ul[@class='car-monthlisting']/li)[1]/a";
        final HtmlAnchor latestPostLink = (HtmlAnchor) page.getByXPath(xpath).get(0);
        final HtmlPage postPage = latestPostLink.click();

        final List<Object> h1 = postPage.getByXPath("//h1");

        Assert.assertTrue(h1.size() > 0);
    }
}
