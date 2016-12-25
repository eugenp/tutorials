package com.baeldung.jsoup;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

public class JsoupExample {

    public static void main(String[] args) throws IOException {
        scrapeSpringBlog();
    }

    static void scrapeSpringBlog() throws IOException {
        String blogUrl = "https://spring.io/blog";
        Document doc = Jsoup.connect(blogUrl).get();

        try {
            Document doc404 = Jsoup.connect("https://spring.io/will-not-be-found").get();
        } catch (HttpStatusException ex) {
            System.out.println(ex.getMessage());
        }

        Document docCustomConn = Jsoup.connect(blogUrl).userAgent("Mozilla").get();
        docCustomConn = Jsoup.connect(blogUrl).timeout(5000).get();
        docCustomConn = Jsoup.connect(blogUrl).cookie("cookiename", "val234").get();
        // docCustomConn = Jsoup.connect(blogUrl).data("datakey", "datavalue").post();
        docCustomConn = Jsoup.connect(blogUrl).header("headersecurity", "xyz123").get();

        docCustomConn = Jsoup.connect(blogUrl)
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("cookiename", "val234")
                .cookie("anothercookie", "ilovejsoup")
                .header("headersecurity", "xyz123")
                .get();

        Elements links = doc.select("a");
        Elements sections = doc.select("section");
        Elements logo = doc.select(".spring-logo--container");
        Elements pagination = doc.select("#pagination_control");
        Elements divsDescendant = doc.select("header div");
        Elements divsDirect = doc.select("header > div");

        Element pag = doc.getElementById("pagination_control");
        Elements desktopOnly = doc.getElementsByClass("desktopOnly");

        Element firstSection = sections.first();
        Element lastSection = sections.last();
        Element secondSection = sections.get(2);
        Elements allParents = firstSection.parents();
        Element parent = firstSection.parent();
        Elements children = firstSection.children();
        Elements siblings = firstSection.siblingElements();

        sections.stream().forEach(el -> System.out.println("section: " + el));

        Elements sectionParagraphs = firstSection.select(".paragraph");

        Element firstArticle = doc.select("article").first();
        Element timeElement = firstArticle.select("time").first();
        String dateTimeOfFirstArticle = timeElement.attr("datetime");
        Element sectionDiv = firstArticle.select("section div").first();
        String sectionDivText = sectionDiv.text();
        String articleHtml = firstArticle.html();
        String outerHtml = firstArticle.outerHtml();

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

        File indexFile = new File("/tmp", "spring_blog_home.html");
        FileUtils.writeStringToFile(indexFile, doc.html(), doc.charset());
    }
}
