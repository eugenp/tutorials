package com.baeldung.string;

import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class SubstringPalindromeUnitTest {

    private static final String INPUT_NITIN = "nitin";
    private static final String INPUT_CIVIC = "civic";
    private static final String INPUT_LEVEL = "level";

    Set<String> EXPECTED_PALINDROME_NITIN = new HashSet<String>() {
        {
            add("nitin");
            add("iti");
            add("n");
            add("i");
            add("t");
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

    Set<String> EXPECTED_PALINDROME_LEVEL = new HashSet<String>() {
        {
            add("level");
            add("eve");
            add("e");
            add("v");
            add("l");
        }
    };

    private SubstringPalindrome palindrome = new SubstringPalindrome();

    @Test
    public void whenApproachUsingManachersAlgo_shouldBePalindrome() {
        assertEquals(EXPECTED_PALINDROME_NITIN, palindrome.isPalindromeUsingManachersAlgo(INPUT_NITIN));
        assertEquals(EXPECTED_PALINDROME_LEVEL, palindrome.isPalindromeUsingManachersAlgo(INPUT_LEVEL));
        assertEquals(EXPECTED_PALINDROME_CIVIC, palindrome.isPalindromeUsingManachersAlgo(INPUT_CIVIC));
    }

    @Test
    public void whenApproachUsingCenter_shouldBePalindrome() {
        assertEquals(EXPECTED_PALINDROME_NITIN, palindrome.isPalindromeUsingCenter(INPUT_NITIN));
        assertEquals(EXPECTED_PALINDROME_LEVEL, palindrome.isPalindromeUsingCenter(INPUT_LEVEL));
        assertEquals(EXPECTED_PALINDROME_CIVIC, palindrome.isPalindromeUsingCenter(INPUT_CIVIC));
    }

    @Test
    public void whenApproachUsingSubstrings_shouldBePalindrome() {
        assertEquals(EXPECTED_PALINDROME_NITIN, palindrome.isPalindromeUsingSubstring(INPUT_NITIN));
        assertEquals(EXPECTED_PALINDROME_LEVEL, palindrome.isPalindromeUsingSubstring(INPUT_LEVEL));
        assertEquals(EXPECTED_PALINDROME_CIVIC, palindrome.isPalindromeUsingSubstring(INPUT_CIVIC));
    }
}
