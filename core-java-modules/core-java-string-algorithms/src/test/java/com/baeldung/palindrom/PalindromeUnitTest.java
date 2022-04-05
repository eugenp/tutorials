package com.baeldung.palindrom;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PalindromeUnitTest {

    private String[] words = {
            "Anna",
            "Civic",
            "Kayak",
            "Level",
            "Madam",
    };

    private String[] sentences = {
            "Sore was I ere I saw Eros",
            "Euston saw I was not Sue",
            "Too hot to hoot",
            "No mists or frost Simon",
            "Stella won no wallets"
    };

    private Palindrome palindrome = new Palindrome();

    @Test
    public void whenWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(palindrome.isPalindrome(word));
    }

    @Test
    public void whenSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(palindrome.isPalindrome(sentence));
    }

    @Test
    public void whenReverseWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(palindrome.isPalindromeReverseTheString(word));
    }

    @Test
    public void whenReverseSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(palindrome.isPalindromeReverseTheString(sentence));
    }

    @Test
    public void whenStringBuilderWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(palindrome.isPalindromeUsingStringBuilder(word));
    }

    @Test
    public void whenStringBuilderSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(palindrome.isPalindromeUsingStringBuilder(sentence));
    }

    @Test
    public void whenStringBufferWord_shouldBePalindrome() {
        for (String word : words)
            assertTrue(palindrome.isPalindromeUsingStringBuffer(word));
    }

    @Test
    public void whenStringBufferSentence_shouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(palindrome.isPalindromeUsingStringBuffer(sentence));
    }

    @Test
    public void whenPalindromeRecursive_wordShouldBePalindrome() {
        for (String word : words)
            assertTrue(palindrome.isPalindromeRecursive(word));
    }

    @Test
    public void whenPalindromeRecursive_sentenceShouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(palindrome.isPalindromeRecursive(sentence));
    }

    @Test
    public void whenPalindromeStreams_wordShouldBePalindrome() {
        for (String word : words)
            assertTrue(palindrome.isPalindromeUsingIntStream(word));
    }

    @Test
    public void whenPalindromeStreams_sentenceShouldBePalindrome() {
        for (String sentence : sentences)
            assertTrue(palindrome.isPalindromeUsingIntStream(sentence));
    }
}
