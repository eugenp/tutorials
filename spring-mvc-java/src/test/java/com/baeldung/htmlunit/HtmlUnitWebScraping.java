package com.baeldung.htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitWebScraping {

    public static void main(final String[] args) throws Exception {
        try (final WebClient webClient = new WebClient()) {

            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

            final HtmlPage page = webClient.getPage("http://www.baeldung.com/full_archive");
            final HtmlAnchor latestPostLink = (HtmlAnchor) page.getByXPath("(//ul[@class='car-monthlisting']/li)[1]/a").get(0);

            System.out.println("Entering: " + latestPostLink.getHrefAttribute());

            final HtmlPage postPage = latestPostLink.click();

            final HtmlHeading1 heading1 = (HtmlHeading1) postPage.getByXPath("//h1").get(0);
            System.out.println("Title: " + heading1.getTextContent());

            final List<HtmlHeading2> headings2 = (List<HtmlHeading2>) postPage.getByXPath("//h2");

            final StringBuilder sb = new StringBuilder(heading1.getTextContent());
            for (final HtmlHeading2 h2 : headings2) {
                sb.append("\n").append(h2.getTextContent());
            }

            System.out.println(sb.toString());
        }
    }

}
