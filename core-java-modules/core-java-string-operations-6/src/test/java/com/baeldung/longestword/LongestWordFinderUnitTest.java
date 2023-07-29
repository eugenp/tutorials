package com.baeldung.longestword;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LongestWordFinderUnitTest {

    LongestWordFinder longestWordFinder = new LongestWordFinder();

    @Test
    void givenNull_whenFindLongestWord_thenEmpty() {
        assertThat(longestWordFinder.findLongestWord(null)).isEmpty();
    }

    @Test
    void givenEmptyString_whenFindLongestWord_thenEmpty() {
        assertThat(longestWordFinder.findLongestWord("")).isEmpty();
    }

    @Test
    void givenStringWithOnlySpaces_whenFindLongestWord_thenEmpty() {
        assertThat(longestWordFinder.findLongestWord("   ")).isEmpty();
    }

    @Test
    void givenAPhraseWithALongestWord_whenFindLongestWord_thenLongestWordOfThePhrase() {
        assertThat(longestWordFinder.findLongestWord("This is a phrase with words")).hasValue("phrase");
    }

    @Test
    void givenAPhraseWithVariousWordsOfMaxLength_whenFindLongestWord_thenAnyOfTheLongestsWordsOfThePhrase() {
        assertThat(longestWordFinder.findLongestWord("Baeldung is another word of size eight in this sentence")
          .get()).isIn("Baeldung", "sentence");
    }

    @Test
    void givenNull_whenFindLongestWords_thenEmpty() {
        assertThat(longestWordFinder.findLongestWords(null)).isEmpty();
    }

    @Test
    void givenEmptyString_whenFindLongestWords_thenEmpty() {
        assertThat(longestWordFinder.findLongestWords("")).isEmpty();
    }

    @Test
    void givenStringWithOnlySpaces_whenFindLongestWords_thenEmpty() {
        assertThat(longestWordFinder.findLongestWords("   ")).isEmpty();
    }

    @Test
    void givenAPhraseWithALongestWord_whenFindLongestWords_thenLongestWordOfThePhrase() {
        assertThat(longestWordFinder.findLongestWords("This is a phrase with words")).containsExactly("phrase");
    }

    @Test
    void givenAPhraseWithVariousWordsOfMaxLength_whenFindLongestWords_thenAllLongestsWords() {
        assertThat(longestWordFinder.findLongestWords("Baeldung is another word of size eight in this sentence")).containsExactly("Baeldung", "sentence");
    }

}
