package com.baeldung.algorithms.palindrome;

public class PalindromeNumber {

    public static boolean isPalindromeIterative(int number) {
        int originalNumber = number;
        int reversedNumber = 0;

        while (number > 0) {
            int digit = number % 10;
            reversedNumber = reversedNumber * 10 + digit;
            number /= 10;
        }

        return originalNumber == reversedNumber;
    }

    public static boolean isPalindromeString(int number) {
        String original = String.valueOf(number);
        String reversed = new StringBuilder(original).reverse()
            .toString();
        return original.equals(reversed);
    }

    public static boolean isPalindromeRecursive(int number) {
        return isPalindromeHelper(number, 0) == number;
    }

    private static int isPalindromeHelper(int number, int reversedNumber) {
        if (number == 0) {
            return reversedNumber;
        }
        reversedNumber = reversedNumber * 10 + number % 10;
        return isPalindromeHelper(number / 10, reversedNumber);
    }

    public static boolean isPalindromeHalfReversal(int number) {
        if (number < 0 || (number % 10 == 0 && number != 0)) {
            return false;
        }

        int reversedNumber = 0;

        while (number > reversedNumber) {
            reversedNumber = reversedNumber * 10 + number % 10;
            number /= 10;
        }

        return number == reversedNumber || number == reversedNumber / 10;
    }

    public static boolean isPalindromeDigitByDigit(int number) {
        if (number < 0) {
            return false;
        }

        int divisor = 1;
        while (number / divisor >= 10) {
            divisor *= 10;
        }

        while (number != 0) {
            int leading = number / divisor;
            int trailing = number % 10;

            if (leading != trailing) {
                return false;
            }

            number = (number % divisor) / 10;

            divisor /= 100;
        }

        return true;
    }
}