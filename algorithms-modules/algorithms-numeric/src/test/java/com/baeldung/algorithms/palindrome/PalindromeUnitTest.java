package com.baeldung.algorithms.palindrome;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PalindromeUnitTest {

    @Test
    void givenNumber_whenCheckNumberAnyApproach_thenNumberIsPalindrome() {
        assertTrue(PalindromeNumber.isPalindromeIterative(31213));
        assertTrue(PalindromeNumber.isPalindromeString(23432));
        assertTrue(PalindromeNumber.isPalindromeRecursive(78987));
        assertTrue(PalindromeNumber.isPalindromeHalfReversal(12321));
        assertTrue(PalindromeNumber.isPalindromeDigitByDigit(89098));
    }

    @Test
    void givenNumber_whenCheckNumberAnyApproach_thenNumberIsNotPalindrome() {
        assertFalse(PalindromeNumber.isPalindromeIterative(10000));
        assertFalse(PalindromeNumber.isPalindromeString(13456));
        assertFalse(PalindromeNumber.isPalindromeRecursive(739127));
        assertFalse(PalindromeNumber.isPalindromeHalfReversal(1212));
        assertFalse(PalindromeNumber.isPalindromeDigitByDigit(7382));
    }
}
