package com.baeldung.htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitWebScraping {

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
	public void givenBaeldungArchive_whenRetrievingArticle_thenHasH1() 
	  throws Exception {
	    webClient.getOptions().setCssEnabled(false);
	    webClient.getOptions().setJavaScriptEnabled(false);

	    String url = "http://www.baeldung.com/full_archive";
	    HtmlPage page = webClient.getPage(url);
	    String xpath = "(//ul[@class='car-monthlisting']/li)[1]/a";
	    HtmlAnchor latestPostLink 
	      = (HtmlAnchor) page.getByXPath(xpath).get(0);
	    HtmlPage postPage = latestPostLink.click();

	    List<HtmlHeading1> h1  
	      = (List<HtmlHeading1>) postPage.getByXPath("//h1");

	    Assert.assertTrue(h1.size() > 0);
	}
}
