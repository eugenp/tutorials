package com.baeldung.escapehtml;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HtmlEscapeUnitTest {

    @Test
    public void whenInputContainAmpersand_thenEscape() {
        String input = "AT&T";
        String expected = "AT&amp;T";
        assertEquals(expected, HtmlEscapeUtils.escapeWithApacheCommons(input));
        assertEquals(expected, HtmlEscapeUtils.escapeWithGuava(input));
        assertEquals(expected, HtmlEscapeUtils.escapeWithSpring(input));
    }

    @Test
    public void whenInputContainDoubleQuotes_thenEscape() {
        String input = "She said, \"Hello!\"";
        String expected = "She said, &quot;Hello!&quot;";
        assertEquals(expected, HtmlEscapeUtils.escapeWithApacheCommons(input));
        assertEquals(expected, HtmlEscapeUtils.escapeWithGuava(input));
        assertEquals(expected, HtmlEscapeUtils.escapeWithSpring(input));
    }

    @Test
    public void whenInputContainManyHtmlSymbols_thenEscape() {
        String input = "<p>This is a <strong>test</strong> string.</p>";
        String expected = "&lt;p&gt;This is a &lt;strong&gt;test&lt;/strong&gt; string.&lt;/p&gt;";
        assertEquals(expected, HtmlEscapeUtils.escapeWithApacheCommons(input));
        assertEquals(expected, HtmlEscapeUtils.escapeWithGuava(input));
        assertEquals(expected, HtmlEscapeUtils.escapeWithSpring(input));
    }

    @Test
    public void whenInputContainNoHtmlSymbols_thenEscape() {
        String input = "This is a plain text.";
        assertEquals(input, HtmlEscapeUtils.escapeWithApacheCommons(input));
        assertEquals(input, HtmlEscapeUtils.escapeWithGuava(input));
        assertEquals(input, HtmlEscapeUtils.escapeWithSpring(input));
    }
}
