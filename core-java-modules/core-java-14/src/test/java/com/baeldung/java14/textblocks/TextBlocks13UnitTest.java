package com.baeldung.java14.textblocks;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TextBlocks13UnitTest {
    private TextBlocks13 subject = new TextBlocks13();

    @Test
    void givenAnOldStyleMultilineString_whenComparing_thenEqualsTextBlock() {
        String expected = "<html>\n"
          + "\n"
          + "    <body>\n"
          + "        <p>example text</p>\n"
          + "    </body>\n"
          + "</html>";
        assertThat(subject.getBlockOfHtml()).isEqualTo(expected);
    }

    @Test
    void givenAnOldStyleString_whenComparing_thenEqualsTextBlock() {
        String expected = "<html>\n\n    <body>\n        <p>example text</p>\n    </body>\n</html>";
        assertThat(subject.getBlockOfHtml()).isEqualTo(expected);
    }

    @Test
    void givenAnIndentedString_thenMatchesIndentedOldStyle() {
        assertThat(subject.getNonStandardIndent()).isEqualTo("    Indent\n");
    }

    @Test
    void givenAMultilineQuery_thenItCanContainUnescapedQuotes() {
        assertThat(subject.getQuery()).contains("select \"id\", \"user\"");
    }

    @Test
    void givenAMultilineQuery_thenItEndWithANewline() {
        assertThat(subject.getQuery()).endsWith("\n");
    }

    @Test
    void givenATextWithCarriageReturns_thenItContainsBoth() {
        assertThat(subject.getTextWithCarriageReturns()).isEqualTo("separated with\r\ncarriage returns");
    }

    @Test
    void givenAStringWithEscapedWhitespace_thenItAppearsInTheResultingString() {
        assertThat(subject.getTextWithEscapes()).contains("fun with\n\n")
            .contains("whitespace\t\r\n")
            .contains("and other escapes \"\"\"");
    }

    @Test
    void givenAFormattedString_thenTheParameterIsReplaced() {
        assertThat(subject.getFormattedText("parameter")).contains("Some parameter: parameter");
    }
}