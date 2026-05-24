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
    void givenMarkdownInput_whenConvertingToHtml_thenReturnRenderedHtml() {
        String html = markDownToHtml("Welcome to *Baeldung*");

        assertEquals("<p>Welcome to <em>Baeldung</em></p>\n", html);
    }

    @Test
    void givenMarkdownInput_whenProcessingParsedNode_thenReturnWordCount() {
        int wordCount = processParsedNode("Welcome to *Baeldung*");

        assertEquals(3, wordCount);
    }

    @Test
    void givenHeadingText_whenConvertingToMarkdown_thenReturnMarkdownHeading() {
        String markdown = htmlToMarkDown("Java Tutorial");

        assertEquals("## Java Tutorial\n", markdown);
    }

    @Test
    void givenImageMarkdown_whenRenderingHtml_thenAddCustomClassAttribute() {
        String html = changingHtmlAttribute("![text](/url.png)");

        assertEquals("<p><img src=\"/url.png\" alt=\"text\" class=\"border\" /></p>\n", html);
    }

    @Test
    void givenIndentedCodeBlock_whenRenderingHtml_thenUseCustomNodeRenderer() {
        String html = customizingHtmlRendering("Example:\n\n    code");

        assertEquals("<p>Example:</p>\n<pre>code\n</pre>\n", html);
    }
}