package com.baeldung.string;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class WhenCheckingPalindrome {

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
        for(String word:words)
            Assert.assertTrue(palindrome.isPalindrome(word));
    }

    @Test
    public void whenSentence_shouldBePalindrome() {
        for(String sentence:sentences)
            Assert.assertTrue(palindrome.isPalindrome(sentence));
    }
    
    @Test
    public void whenReverseWord_shouldBePalindrome() {
        for(String word:words)
            Assert.assertTrue(palindrome.isPalindromeReverseTheString(word));
    }
    
    @Test
    public void whenReverseSentence_shouldBePalindrome() {
        for(String sentence:sentences)
            Assert.assertTrue(palindrome.isPalindromeReverseTheString(sentence));
    }
    
    @Test
    public void whenStringBuilderWord_shouldBePalindrome() {
        for(String word:words)
            Assert.assertTrue(palindrome.isPalindromeUsingStringBuilder(word));
    }
    
    @Test
    public void whenStringBuilderSentence_shouldBePalindrome() {
        for(String sentence:sentences)
            Assert.assertTrue(palindrome.isPalindromeUsingStringBuilder(sentence));
    }
    
    @Test
    public void whenStringBufferWord_shouldBePalindrome() {
        for(String word:words)
            Assert.assertTrue(palindrome.isPalindromeUsingStringBuffer(word));
    }
    
    @Test
    public void whenStringBufferSentence_shouldBePalindrome() {
        for(String sentence:sentences)
            
            Assert.assertTrue(palindrome.isPalindromeUsingStringBuffer(sentence));
    }
    
    @Test
    public void whenPalindromeRecursive_wordShouldBePalindrome() {
        for(String word:words) {
            word = word.replaceAll("\\s+", "").toLowerCase();
            int backward = word.length()-1;
            
            Assert.assertTrue(palindrome.isPalindromeRecursive(word,0,backward));
        }
    }
    
    @Test
    public void whenPalindromeRecursive_sentenceShouldBePalindrome() {
        for(String sentence:sentences) {
            sentence = sentence.replaceAll("\\s+", "").toLowerCase();
            int backward = sentence.length()-1;
            
            Assert.assertTrue(palindrome.isPalindromeRecursive(sentence,0,backward));
        }
    }
    
    @Test
    public void whenPalindromeStreams_wordShouldBePalindrome() {
        String[] expected = Arrays.stream(words)
            .filter(palindrome::isPalindrome)
            .toArray(String[]::new);
        
        Assert.assertArrayEquals(expected, words);
    }
    
    @Test
    public void whenPalindromeStreams_sentenceShouldBePalindrome() {
        String[] expected = Arrays.stream(sentences)
            .filter(palindrome::isPalindrome)
            .toArray(String[]::new);
        
        Assert.assertArrayEquals(expected, sentences);
    }
}
