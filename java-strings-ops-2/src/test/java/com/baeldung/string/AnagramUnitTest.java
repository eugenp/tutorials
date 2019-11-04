package com.baeldung.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnagramUnitTest {
    @Test
    public void whenAnagram_thenReturnTrue() {
       Anagram anagram = new Anagram();
       String str1 = "abcab";
       String str2 = "cabba";
       assertTrue(anagram.isAnagramSort(str1, str2));
       assertTrue(anagram.isAnagramCouting(str1, str2));
       assertTrue(anagram.isAnagramMultiset(str1, str2));
    }

    @Test
    public void whenNotAnagram_thenReturnFalse() {
       Anagram anagram = new Anagram();
       String str1 = "abcaba";
       String str2 = "cabbac";
       assertFalse(anagram.isAnagramSort(str1, str2));
       assertFalse(anagram.isAnagramCouting(str1, str2));
       assertFalse(anagram.isAnagramMultiset(str1, str2));
    }
}
