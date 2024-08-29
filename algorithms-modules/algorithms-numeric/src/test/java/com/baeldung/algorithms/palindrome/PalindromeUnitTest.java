package com.baeldung.algorithms.palindrome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PalindromeUnitTest {

    @Test
    void givenNumber_whenCheckNumberWithIterativeApproach_thenNumberIsPalindrome() {
        int number = 12321;
        int originalNumber = number;
        int reversedNumber = 0;

        while (number > 0) {
            int digit = number % 10;
            reversedNumber = reversedNumber * 10 + digit;
            number /= 10;
        }

        assertEquals(originalNumber, reversedNumber);
    }

    @Test
    void givenNumber_whenCheckNumberWithIterativeApproach_thenNumberIsNotPalindrome() {
        int number = 123;
        int originalNumber = number;
        int reversedNumber = 0;

        while (number > 0) {
            int digit = number % 10;
            reversedNumber = reversedNumber * 10 + digit;
            number /= 10;
        }

        assertNotEquals(originalNumber, reversedNumber);
    }

    @Test
    void givenNumber_whenCheckNumberWithStringApproach_thenNumberIsPalindrome(){
        int number = 12321;
        String original = String.valueOf(number);
        String reversed = new StringBuilder(original).reverse()
            .toString();
        assertEquals(original, reversed);
    }

    @Test
    void givenNumber_whenCheckNumberWithStringApproach_thenNumberIsNotPalindrome(){
        int number = 123;
        String original = String.valueOf(number);
        String reversed = new StringBuilder(original).reverse()
            .toString();
        assertNotEquals(original, reversed);
    }

    @Test
    void givenNumber_whenCheckNumberWithRecursiveApproach_thenNumberIsPalindrome(){
        int number = 12321;
        assertTrue(isPalindrome(number), number + " should be a palindrome");
    }

    @Test
    void givenNumber_whenCheckNumberWithRecursiveApproach_thenNumberIsNotPalindrome(){
        int number = 123;
        assertFalse(isPalindrome(number), number + " should not be a palindrome");
    }

    @Test
    void givenNumber_whenCheckNumberWithHalfReversalApproach_thenNumberIsPalindrome(){
        int number = 12321;
        int originalNumber = number;
        if (number < 0 || (number % 10 == 0 && number != 0)) {
            assertFalse(true, "Invalid input should return false");
        }
        int reversedNumber = 0;
        while (number > reversedNumber) {
            reversedNumber = reversedNumber * 10 + number % 10;
            number /= 10;
        }
        boolean result = (number == reversedNumber || number == reversedNumber / 10);
        assertTrue(result, originalNumber + " should be a palindrome");
    }

    @Test
    void givenNumber_whenCheckNumberWithHalfReversalApproach_thenNumberIsNotPalindrome(){
        int number = 123;
        int originalNumber = number;
        int reversedNumber = 0;
        while (number > reversedNumber) {
            reversedNumber = reversedNumber * 10 + number % 10;
            number /= 10;
        }
        boolean result = (number == reversedNumber || number == reversedNumber / 10);
        assertFalse(result, originalNumber + " should not be a palindrome");
    }

    @Test
    void givenNumber_whenCheckNumberWithDigitByDigitApproach_thenNumberIsPalindrome(){
        int number = 12321;
        int originalNumber = number;
        if (number < 0) {
            assertFalse(true, "Negative numbers should return false");
        }
        int divisor = 1;
        while (number / divisor >= 10) {
            divisor *= 10;
        }
        boolean isPalindrome = true;
        while (number != 0) {
            int leading = number / divisor;
            int trailing = number % 10;
            if (leading != trailing) {
                isPalindrome = false;
                break;
            }
            number = (number % divisor) / 10;
            divisor /= 100;
        }
        assertTrue(isPalindrome, originalNumber + " should be a palindrome");
    }

    @Test
    void givenNumber_whenCheckNumberWithDigitByDigitApproach_thenNumberIsNotPalindrome(){
        int number = 123;
        int originalNumber = number;
        int divisor = 1;
        while (number / divisor >= 10) {
            divisor *= 10;
        }
        boolean isPalindrome = true;
        while (number != 0) {
            int leading = number / divisor;
            int trailing = number % 10;

            if (leading != trailing) {
                isPalindrome = false;
                break;
            }
            number = (number % divisor) / 10;
            divisor /= 100;
        }
        assertFalse(isPalindrome, originalNumber + " should not be a palindrome");
    }

    int isPalindromeHelper(int number, int reversedNumber) {
        if (number == 0) {
            return reversedNumber;
        }
        reversedNumber = reversedNumber * 10 + number % 10;
        return isPalindromeHelper(number / 10, reversedNumber);
    }

    boolean isPalindrome(int number) {
        return isPalindromeHelper(number, 0) == number;
    }
}
