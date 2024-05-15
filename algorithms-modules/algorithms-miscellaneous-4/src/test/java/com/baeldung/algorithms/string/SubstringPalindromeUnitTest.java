package com.baeldung.algorithms.string;

import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class SubstringPalindromeUnitTest {

    private static final String INPUT_BUBBLE = "bubble";
    private static final String INPUT_CIVIC = "civic";
    private static final String INPUT_INDEED = "indeed";
    private static final String INPUT_ABABAC = "ababac";

    Set<String> EXPECTED_PALINDROME_BUBBLE = new HashSet<String>() {
        {
            add("b");
            add("u");
            add("l");
            add("e");
            add("bb");
            add("bub");
        }
    };

    Set<String> EXPECTED_PALINDROME_CIVIC = new HashSet<String>() {
        {
            add("civic");
            add("ivi");
            add("i");
            add("c");
            add("v");
        }
    };

    Set<String> EXPECTED_PALINDROME_INDEED = new HashSet<String>() {
        {
            add("i");
            add("n");
            add("d");
            add("e");
            add("ee");
            add("deed");
        }
    };

    Set<String> EXPECTED_PALINDROME_ABABAC = new HashSet<String>() {
        {
            add("a");
            add("b");
            add("c");
            add("aba");
            add("bab");
            add("ababa");
        }
    };

    private SubstringPalindrome palindrome = new SubstringPalindrome();

    @Test
    public void whenUsingManachersAlgorithm_thenFindsAllPalindromes() {
        assertEquals(EXPECTED_PALINDROME_BUBBLE, palindrome.findAllPalindromesUsingManachersAlgorithm(INPUT_BUBBLE));
        assertEquals(EXPECTED_PALINDROME_INDEED, palindrome.findAllPalindromesUsingManachersAlgorithm(INPUT_INDEED));
        assertEquals(EXPECTED_PALINDROME_CIVIC, palindrome.findAllPalindromesUsingManachersAlgorithm(INPUT_CIVIC));
        assertEquals(EXPECTED_PALINDROME_ABABAC, palindrome.findAllPalindromesUsingManachersAlgorithm(INPUT_ABABAC));
    }

    @Test
    public void whenUsingCenterApproach_thenFindsAllPalindromes() {
        assertEquals(EXPECTED_PALINDROME_BUBBLE, palindrome.findAllPalindromesUsingCenter(INPUT_BUBBLE));
        assertEquals(EXPECTED_PALINDROME_INDEED, palindrome.findAllPalindromesUsingCenter(INPUT_INDEED));
        assertEquals(EXPECTED_PALINDROME_CIVIC, palindrome.findAllPalindromesUsingCenter(INPUT_CIVIC));
        assertEquals(EXPECTED_PALINDROME_ABABAC, palindrome.findAllPalindromesUsingCenter(INPUT_ABABAC));
    }

    @Test
    public void whenUsingBruteForceApproach_thenFindsAllPalindromes() {
        assertEquals(EXPECTED_PALINDROME_BUBBLE, palindrome.findAllPalindromesUsingBruteForceApproach(INPUT_BUBBLE));
        assertEquals(EXPECTED_PALINDROME_INDEED, palindrome.findAllPalindromesUsingBruteForceApproach(INPUT_INDEED));
        assertEquals(EXPECTED_PALINDROME_CIVIC, palindrome.findAllPalindromesUsingBruteForceApproach(INPUT_CIVIC));
        assertEquals(EXPECTED_PALINDROME_ABABAC, palindrome.findAllPalindromesUsingBruteForceApproach(INPUT_ABABAC));
    }
}
