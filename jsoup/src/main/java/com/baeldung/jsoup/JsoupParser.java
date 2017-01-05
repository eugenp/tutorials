package com.baeldung.jsoup;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class JsoupParser {

    Document doc;

    public void loadDocument(String blogUrl) throws IOException {
        doc = Jsoup.connect(blogUrl).get();
    }

    void loadDocumentCustomized(String blogUrl) throws IOException {
        doc = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .referrer("http://google.com")
                .header("headersecurity", "xyz123")
                .get();
    }

    void examplesSelectors() {
        Elements links = doc.select("a");
        Elements logo = doc.select(".spring-logo--container");
        Elements pagination = doc.select("#pagination_control");
        Elements divsDescendant = doc.select("header div");
        Elements divsDirect = doc.select("header > div");

        Element pag = doc.getElementById("pagination_control");
        Elements desktopOnly = doc.getElementsByClass("desktopOnly");

        Elements sections = doc.select("section");
        Element firstSection = sections.first();
        Elements sectionParagraphs = firstSection.select(".paragraph");
    }

    void examplesTraversing() {
        Elements sections = doc.select("section");

        Element firstSection = sections.first();
        Element lastSection = sections.last();
        Element secondSection = sections.get(2);
        Elements allParents = firstSection.parents();
        Element parent = firstSection.parent();
        Elements children = firstSection.children();
        Elements siblings = firstSection.siblingElements();

        sections.stream().forEach(el -> System.out.println("section: " + el));
    }

    void examplesExtracting() {
        Element firstArticle = doc.select("article").first();
        Element timeElement = firstArticle.select("time").first();
        String dateTimeOfFirstArticle = timeElement.attr("datetime");
        Element sectionDiv = firstArticle.select("section div").first();
        String sectionDivText = sectionDiv.text();
        String articleHtml = firstArticle.html();
        String outerHtml = firstArticle.outerHtml();
    }

    void examplesModifying() {
        Element firstArticle = doc.select("article").first();
        Element timeElement = firstArticle.select("time").first();
        Element sectionDiv = firstArticle.select("section div").first();

        String dateTimeOfFirstArticle = timeElement.attr("datetime");
        timeElement.attr("datetime", "2016-12-16 15:19:54.3");
        sectionDiv.text("foo bar");
        firstArticle.select("h2").html("<div><span></span></div>");

        Element link = new Element(Tag.valueOf("a"), "")
                .text("Checkout this amazing website!")
                .attr("href", "http://baeldung.com")
                .attr("target", "_blank");
        firstArticle.appendChild(link);

        doc.select("li.navbar-link").remove();
        firstArticle.select("img").remove();
    }

    String getTidyHtml() {
        return doc.html();
    }
}
