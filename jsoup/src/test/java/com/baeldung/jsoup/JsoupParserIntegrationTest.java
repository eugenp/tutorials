package com.baeldung.jsoup;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsoupParserIntegrationTest {

    private Document doc;

    @Before
    public void setUp() throws IOException {
        doc = Jsoup.connect("https://spring.io/blog")
            .get();
    }

    @Test
    public void loadDocument404() throws IOException {
        try {
            doc = Jsoup.connect("https://spring.io/will-not-be-found")
                .get();
        } catch (HttpStatusException ex) {
            assertEquals(404, ex.getStatusCode());
        }
    }

    @Test
    public void loadDocumentCustomized() throws IOException {
        doc = Jsoup.connect("https://spring.io/blog")
            .userAgent("Mozilla")
            .timeout(5000)
            .cookie("cookiename", "val234")
            .cookie("anothercookie", "ilovejsoup")
            .referrer("http://google.com")
            .header("headersecurity", "xyz123")
            .get();
    }

    @Test
    public void examplesSelectors() throws IOException {
        Elements links = doc.select("a");
        Elements logo = doc.select(".spring-logo--container");
        Elements pagination = doc.select("#pagination_control");
        Elements divsDescendant = doc.select("header div");
        Elements divsDirect = doc.select("header > div");

        Element pag = doc.getElementById("pagination_control");
        Elements desktopOnly = doc.getElementsByClass("desktopOnly");

        Elements articles = doc.select("article");
        Element firstArticle = articles.first();
        Elements sectionParagraphs = firstArticle.select(".paragraph");
    }

    @Test
    public void examplesTraversing() throws IOException {
        Elements articles = doc.select("article");

        Element firstArticle = articles.first();
        Element lastSection = articles.last();
        Element secondSection = articles.get(2);
        Elements allParents = firstArticle.parents();
        Element parent = firstArticle.parent();
        Elements children = firstArticle.children();
        Elements siblings = firstArticle.siblingElements();

        articles.forEach(el -> System.out.println("article: " + el));
    }

    @Test
    public void examplesExtracting() throws IOException {
        Element firstArticle = doc.select("article")
            .first();
        Element titleElement = firstArticle.select("h1 a")
            .first();

        String titleText = titleElement.text();
        String articleHtml = firstArticle.html();
        String outerHtml = firstArticle.outerHtml();
    }

    @Test
    public void examplesModifying() throws IOException {
        Element firstArticle = doc.select("article")
            .first();
        Element h1Element = firstArticle.select("h1")
            .first();

        h1Element.text("foo bar");
        firstArticle.select("h2")
            .html("<div><span></span></div>");

        Element link = new Element(Tag.valueOf("a"), "").text("Checkout this amazing website!")
            .attr("href", "http://baeldung.com")
            .attr("target", "_blank");
        firstArticle.appendChild(link);

        doc.select("li.navbar-link")
            .remove();
        firstArticle.select("img")
            .remove();

        assertTrue(doc.html()
            .contains("http://baeldung.com"));
    }
}
