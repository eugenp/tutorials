package com.baeldung.matchwords;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MatchWordsUnitTest {

    private final String[] words = {"hello", "Baeldung"};
    private final String inputString = "hello there, Baeldung";
    private final String wholeInput = "helloBaeldung";

    @Test
    public void givenText_whenCallingStringContains_shouldMatchWords() {
        final boolean result = MatchWords.containsWords(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingJava8_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsJava8(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingJava8_shouldNotMatchWords() {
        final boolean result = MatchWords.containsWordsJava8(wholeInput, words);
        assertThat(result).isFalse();
    }

    @Test
    public void givenText_whenCallingPattern_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsPatternMatch(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingAhoCorasick_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsAhoCorasick(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingAhoCorasick_shouldNotMatchWords() {
        final boolean result = MatchWords.containsWordsAhoCorasick(wholeInput, words);
        assertThat(result).isFalse();
    }

    @Test
    public void givenText_whenCallingIndexOf_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsIndexOf(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingArrayList_shouldMatchWords() {
        final boolean result = MatchWords.containsWordsArray(inputString, words);
        assertThat(result).isTrue();
    }

    @Test
    public void givenText_whenCallingArrayList_shouldNotMatchWords() {
        final boolean result = MatchWords.containsWordsArray(wholeInput, words);
        assertThat(result).isFalse();
    }
}
