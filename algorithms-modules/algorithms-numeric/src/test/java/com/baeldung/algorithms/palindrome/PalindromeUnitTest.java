package com.baeldung.algorithms.palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PalindromeUnitTest {

    @Test
    void givenNumber_whenUsingIterativeApproach_thenCheckPalindrome() {
        assertTrue(PalindromeNumber.isPalindromeIterative(12321));
        assertFalse(PalindromeNumber.isPalindromeIterative(123));
    }

    @Test
    void givenNumber_whenUsingStringApproach_thenCheckPalindrome() {
        assertTrue(PalindromeNumber.isPalindromeString(12321));
        assertFalse(PalindromeNumber.isPalindromeString(123));
    }
    @Test
    void givenNumber_whenUsingRecursiveApproach_thenCheckPalindrome() {
        assertTrue(PalindromeNumber.isPalindromeRecursive(12321));
        assertFalse(PalindromeNumber.isPalindromeRecursive(123));
    }

    @Test
    void givenNumber_whenUsingHalfReversalApproach_thenCheckPalindrome() {
        assertTrue(PalindromeNumber.isPalindromeHalfReversal(12321));
        assertFalse(PalindromeNumber.isPalindromeHalfReversal(123));
    }
    @Test
    void givenNumber_whenUsingDigitByDigitApproach_thenCheckPalindrome() {
        assertTrue(PalindromeNumber.isPalindromeDigitByDigit(12321));
        assertFalse(PalindromeNumber.isPalindromeDigitByDigit(123));
    }
}
