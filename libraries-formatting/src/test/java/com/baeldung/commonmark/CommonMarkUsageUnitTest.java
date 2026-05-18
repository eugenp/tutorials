package com.baeldung.commonmark;

import static com.baeldung.commonmark.CommonMarkUsage.changingHtmlAttribute;
import static com.baeldung.commonmark.CommonMarkUsage.customizingHtmlRendering;
import static com.baeldung.commonmark.CommonMarkUsage.htmlToMarkDown;
import static com.baeldung.commonmark.CommonMarkUsage.markDownToHtml;
import static com.baeldung.commonmark.CommonMarkUsage.processParsedNode;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommonMarkUsageUnitTest {

    @Test
    void markDownToHtmlTest() {
        String html = markDownToHtml("Welcome to *Baeldung*");
        assertEquals("<p>Welcome to <em>Baeldung</em></p>\n", html);
    }

    @Test
    void visitorCountTest() {
        int html = processParsedNode("Welcome to *Baeldung*");
        assertEquals(3, html);
    }

    @Test
    void htmlToMarkdownTest() {
        String html = htmlToMarkDown("Java Tutorial");
        assertEquals("## Java Tutorial\n", html);
    }

    @Test
    void changingHtmlAttributeTest() {
        String html = changingHtmlAttribute("![text](/url.png)");
        assertEquals("<p><img src=\"/url.png\" alt=\"text\" class=\"border\" /></p>\n", html);
    }

    @Test
    void customizingHtmlRendererTest() {
        String html = customizingHtmlRendering("![text](/url.png)");
        assertEquals("<p><img src=\"/url.png\" alt=\"text\" /></p>\n", html);
    }
}