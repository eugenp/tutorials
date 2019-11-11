package com.baeldung.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnagramUnitTest {
    @Test
    public void givenAnagram_whenUsingSort_thenIdentifyAnagram() {
        Anagram anagram = new Anagram();
        String string1 = "abcab";
        String string2 = "cabba";
        assertTrue(anagram.isAnagramSort(string1, string2));
    }

    @Test
    public void givenAnagram_whenUsingCounting_thenIdentifyAnagram() {
        Anagram anagram = new Anagram();
        String string1 = "abcab";
        String string2 = "cabba";
        assertTrue(anagram.isAnagramCounting(string1, string2));
    }

    @Test
    public void givenAnagram_whenUsingMultiset_thenIdentifyAnagram() {
        Anagram anagram = new Anagram();
        String string1 = "abcab";
        String string2 = "cabba";
        assertTrue(anagram.isAnagramMultiset(string1, string2));
    }

    @Test
    public void givenNonAnagram_whenUsingSort_thenIdentifyNotAnagram() {
        Anagram anagram = new Anagram();
        String string1 = "abcaba";
        String string2 = "cabbac";
        assertFalse(anagram.isAnagramSort(string1, string2));
    }

    @Test
    public void givenNonAnagram_whenUsingCounting_thenIdentifyNotAnagram() {
        Anagram anagram = new Anagram();
        String string1 = "abcaba";
        String string2 = "cabbac";
        assertFalse(anagram.isAnagramCounting(string1, string2));
    }

    @Test
    public void givenNonAnagram_whenUsingMultiset_thenIdentifyNotAnagram() {
        Anagram anagram = new Anagram();
        String string1 = "abcaba";
        String string2 = "cabbac";
        assertFalse(anagram.isAnagramMultiset(string1, string2));
    }
}
