package com.baeldung.java14.textblocks;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TextBlocks14UnitTest {
    private TextBlocks14 subject = new TextBlocks14();

    @Test
    void givenAStringWithEscapedNewLines_thenTheResultHasNoNewLines() {
        String expected = "This is a long test which looks to have a newline but actually does not";
        assertThat(subject.getIgnoredNewLines()).isEqualTo(expected);
    }

    @Test
    void givenAStringWithEscapesSpaces_thenTheResultHasLinesEndingWithSpaces() {
        String expected = "line 1\nline 2        \n";
        assertThat(subject.getEscapedSpaces()).isEqualTo(expected);
    }
}