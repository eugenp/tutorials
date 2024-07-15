package com.baeldung.unescapehtml;


import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.nodes.Entities;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.HtmlUtils;
import org.unbescape.html.HtmlEscape;

public class HtmlUnescapeUnitTest {
    @Test
    public void givenAStringsWithHTMLCharacters_whenConvertedWithApacheCommons_thenUnescape(){
        String expectedQuote = "\"Hello\" Baeldung";
        String escapedQuote = "&quot;Hello&quot; Baeldung";
        Assert.assertEquals(expectedQuote, StringEscapeUtils.unescapeHtml4(escapedQuote));

        String escapedStringsWithHtmlSymbol = "&lt;p&gt;&lt;strong&gt;Test sentence in bold type.&lt;/strong&gt;&lt;/p&gt;";
        String expectedStringsWithHtmlSymbol = "<p><strong>Test sentence in bold type.</strong></p>";
        Assert.assertEquals(expectedStringsWithHtmlSymbol, StringEscapeUtils.unescapeHtml4(escapedStringsWithHtmlSymbol));
    }
    @Test
    public void givenAStringsWithHTMLCharacters_whenConvertedWithSpringHtmlUtil_thenUnescape() {
        String expectedQuote = "\"Code smells\" -Martin Fowler";
        String escapedQuote = "&quot;Code smells&quot; -Martin Fowler";
        Assert.assertEquals(expectedQuote, HtmlUtils.htmlUnescape(escapedQuote));

        String escapedStringsWithHtmlSymbol = "&lt;p&gt;Loren Ipsum is a popular paragraph.&lt;/p&gt;";
        String expectedStringsWithHtmlSymbol = "<p>Loren Ipsum is a popular paragraph.</p>";
        Assert.assertEquals(expectedStringsWithHtmlSymbol, HtmlUtils.htmlUnescape(escapedStringsWithHtmlSymbol));
    }

    @Test
    public void givenAStringsWithHTMLCharacters_whenConvertedWithUnbescape_thenUnescape() {
        String expectedQuote = "\"Carpe diem\" -Horace";
        String escapedQuote = "&quot;Carpe diem&quot; -Horace";
        Assert.assertEquals(expectedQuote, HtmlEscape.unescapeHtml(escapedQuote));

        String escapedStringsWithHtmlSymbol = "&lt;p&gt;&lt;em&gt;Pizza is a famous Italian food. Duh.&lt;/em&gt;&lt;/p&gt;";
        String expectedStringsWithHtmlSymbol = "<p><em>Pizza is a famous Italian food. Duh.</em></p>";
        Assert.assertEquals(expectedStringsWithHtmlSymbol, HtmlEscape.unescapeHtml(escapedStringsWithHtmlSymbol));
    }
    @Test
    public void givenAStringsWithHTMLCharacters_whenConvertedWithJsoup_thenUnescape() {
        String expectedQuote = "\"Jsoup\" is another strong library";
        String escapedQuote = "&quot;Jsoup&quot; is another strong library";
        Assert.assertEquals(expectedQuote,  Entities.unescape(escapedQuote));

        String escapedStringsWithHtmlSymbol = "&lt;p&gt;It simplifies working with real-world &lt;strong&gt;HTML&lt;/strong&gt; and &lt;strong&gt;XML&lt;/strong&gt;&lt;/p&gt;";
        String expectedStringsWithHtmlSymbol = "<p>It simplifies working with real-world <strong>HTML</strong> and <strong>XML</strong></p>";
        Assert.assertEquals(expectedStringsWithHtmlSymbol,  Entities.unescape(escapedStringsWithHtmlSymbol));
    }
}
