package com.baeldung.xmlhtml.delhtmltags;

import net.htmlparser.jericho.Renderer;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

class RemoveHtmlTagsLiveTest {

    @Test
    void givenHtml1_whenRemoveTagsByRegex_thenPrintText() throws IOException, URISyntaxException {
        String html = new String(Files.readAllBytes(
            (Paths.get(getClass().getResource("/xmlhtml/delhtmltags/example1.html").toURI()))));
        String result = html.replaceAll("<[^>]*>", "")
            .replaceAll("(?m)^\\s*$", ""); // remove empty and blank lines
        System.out.println(result);
    }

    @Test
    void givenHtml2_whenRemoveTagsByRegex_thenPrintText() throws IOException, URISyntaxException {
        String html = new String(Files.readAllBytes(
            (Paths.get(getClass().getResource("/xmlhtml/delhtmltags/example2.html").toURI()))));
        String result = html.replaceAll("<[^>]*>", "");
        System.out.println(result);
    }

    @Test
    void givenHtml2_whenRemoveTagsByJsoup_thenPrintText() throws IOException, URISyntaxException {
        String html = new String(Files.readAllBytes(
            (Paths.get(getClass().getResource("/xmlhtml/delhtmltags/example2.html").toURI()))));
        System.out.println(Jsoup.parse(html).text());
    }

    @Test
    void givenHtml2_whenRemoveTagsByHtmlCleaner_thenPrintText() throws IOException, URISyntaxException {
        String html = new String(Files.readAllBytes(
            (Paths.get(getClass().getResource("/xmlhtml/delhtmltags/example2.html").toURI()))));
        CleanerProperties props = new CleanerProperties();
        props.setPruneTags("script");
        String result = new HtmlCleaner(props).clean(html).getText().toString();
        System.out.println(result);
    }

    @Test
    void givenHtml2_whenRemoveTagsByJericho_thenPrintText() throws IOException, URISyntaxException {
        String html = new String(Files.readAllBytes(
            (Paths.get(getClass().getResource("/xmlhtml/delhtmltags/example2.html").toURI()))));
        Source htmlSource = new Source(html);
        Segment segment = new Segment(htmlSource, 0, htmlSource.length());
        Renderer htmlRender = new Renderer(segment).setIncludeHyperlinkURLs(true);
        System.out.println(htmlRender);
    }
}
