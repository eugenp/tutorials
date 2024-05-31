package com.baeldung.unescapehtml;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.baeldung.Unescapehtml.HtmlUnescapeUtils;

public class HtmlUnescapeUtilsTest {
    @Test
    public void givenAStringWithEscapedAmpersand_whenConverted_thenUnescape() {
        String escapedText = "&#38;";
        String expectedText = "&";
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithApacheCommons(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithSpring(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithUnbescape(escapedText));

        escapedText = "&amp;";
        expectedText = "&";
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithApacheCommons(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithSpring(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithUnbescape(escapedText));
    }

    @Test
    public void givenAStringWithDoubleQuotes_whenConverted_thenUnescape() {
        String expectedText = "\"Hello\" Baeldung";
        String escapedText = "&quot;Hello&quot; Baeldung";

        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithApacheCommons(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithSpring(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithUnbescape(escapedText));
    }

    @Test
    public void givenAStringWithHtmlSymbols_whenConverted_thenUnescape() {
        String expectedText = "<p><strong>Test sentence in bold type.</strong></p>";
        String escapedText = "&lt;p&gt;&lt;strong&gt;Test sentence in bold type.&lt;/strong&gt;&lt;/p&gt;";

        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithApacheCommons(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithSpring(escapedText));
        Assert.assertEquals(expectedText, HtmlUnescapeUtils.unescapeWithUnbescape(escapedText));
    }

}
