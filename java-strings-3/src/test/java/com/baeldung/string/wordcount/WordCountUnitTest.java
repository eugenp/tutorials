package com.baeldung.string.wordcount;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class WordCountUnitTest {
    private String string1 = "This is a test sentence with eight words";
    private String string2 = "This#is%a test sentence with eight       words";

    @Test
    public void givenStringWith8Words_whenUsingRegexCount_ThenResultEqual8() {
        assertEquals(8, WordCounter.countWordsUsingRegex(string2));
    }

    @Test
    public void givenStringWith8Words_whenUsingManualMethod_ThenWordCountEqual8() {
        assertEquals(8, WordCounter.countWordsManually(string1));
    }

    @Test
    public void givenAStringWith8Words_whenUsingTokenizer_ThenWordCountEqual8() {
        assertEquals(8, WordCounter.countWordsUsingTokenizer(string1));
    }
}
