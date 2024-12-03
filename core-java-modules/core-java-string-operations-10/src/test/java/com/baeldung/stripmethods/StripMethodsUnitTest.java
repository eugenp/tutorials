package com.baeldung.stripmethods;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StripMethodsUnitTest {

    private static String s = " Baeldung ";

    @Test
    void givenString_whenUsingStripLeadingMethod_thenRemoveLeadingWhitespace() {
        assertThat(s.stripLeading()).doesNotStartWith(" ")
            .endsWith(" ")
            .isEqualTo("Baeldung ");
    }

    @Test
    void givenString_whenUsingStripMethod_thenRemoveTrailingLeadingWhitespace() {
        assertThat(s.strip()).doesNotStartWith(" ")
            .doesNotEndWith(" ")
            .isEqualTo("Baeldung");
    }

    @Test
    void givenString_whenUsingStripTrailingMethod_thenRemoveTrailingWhitespace() {
        assertThat(s.stripTrailing()).startsWith(" ")
            .doesNotEndWith(" ")
            .isEqualTo(" Baeldung");
    }

    @Test
    void givenStringWithUnicodeForExclamationMark_whenUsingTrimMethod_thenObtainOriginalStringValue() {
        assertThat("Baeldung\u0021".trim()).isEqualTo("Baeldung!");
    }

    @Test
    void givenStringWithUnicodeForNull_whenUsingStripMethod_thenObtainOriginalStringValue() {
        assertThat("Baeldung\u0000".strip()).isEqualTo("Baeldung\u0000");
    }

    @Test
    void givenStringWithUnicodeForNull_whenUsingTrimMethod_thenRemoveUnicodeForNull() {
        assertThat("Baeldung\u0000".trim()).isEqualTo("Baeldung");
    }

    @Test
    void givenTextBlockWithExtraSpaceForEachNewLine_whenUsingStripIndent_thenIndentTextBlock() {
        String textBlock = """
            B
             a
              e
               l
                d
                 u
                  n
                   g""";
        assertThat(textBlock.stripIndent()).isEqualTo("B\n a\n  e\n   l\n    d\n     u\n      n\n       g");
    }

    @Test
    void givenTextBlockWithFourthLineAsLeftMost_whenUsingStripIndent_thenIndentTextBlock() {
        String textBlock = """
             B
              a
               e
            l
                 d
                  u
                   n
                    g""";

        assertThat(textBlock.stripIndent()).isEqualTo(" B\n  a\n   e\nl\n     d\n      u\n       n\n        g");
    }

    @Test
    void givenWhitespaceString_whenUsingStripLeadingMethod_thenObtainEmptyString() {
        assertThat(" ".stripLeading()).isEmpty();
    }

    @Test
    void givenWhitespaceString_whenUsingStripMethod_thenObtainEmptyString() {
        assertThat(" ".strip()).isEmpty();
    }

    @Test
    void givenWhitespaceString_whenUsingStripTrailingMethod_thenObtainEmptyString() {
        assertThat(" ".stripTrailing()).isEmpty();
    }
}