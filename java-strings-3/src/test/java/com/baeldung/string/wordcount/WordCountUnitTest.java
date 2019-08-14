package com.baeldung.string.wordcount;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
public class WordCountUnitTest {
    private String string1 = "This is a test sentence with eight words";
    private String string2 = "This#is%a test sentence with eight       words";

    @Test
    public void givenStringWith8Words_whenUsingRegexCount_ThenResultEqual8() {
        assertThat(WordCounter.countWordsUsingRegex(string2) == 8).isTrue();
    }

    @Test
    public void givenStringWith8Words_whenUsingManualMethod_ThenWordCountEqual8() {
        assertThat(WordCounter.countWordsManually(string1) == 8).isTrue();
    }

    @Test
    public void givenAStringWith8Words_whenUsingTokenizer_ThenWordCountEqual8() {
        assertThat(WordCounter.countWordsUsingTokenizer(string1) == 8).isTrue();
    }
}
