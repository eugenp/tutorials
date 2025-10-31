package com.baeldung.algorithms.sumoftwosquares;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NumberAsSumOfTwoSquares {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberAsSumOfTwoSquares.class);

    /**
     * Checks if a non-negative integer n can be written as the
     * sum of two squares i.e. (a^2 + b^2)
     * This implementation is based on Fermat's theorem on sums of two squares.
     *
     * @param n The number to check (must be non-negative).
     * @return true if n can be written as a sum of two squares, false otherwise.
     */
    public static boolean isSumOfTwoSquares(int n) {
        if (n < 0) {
            LOGGER.warn("Input must be non-negative. Returning false for n = {}", n);
            return false;
        }
        if (n == 0) {
            return true; // 0 = 0^2 + 0^2
        }

        // 1. Reduce n to an odd number if n is even.
        while (n % 2 == 0) {
            n /= 2;
        }

        // 2. Iterate through odd prime factors starting from 3
        for (int i = 3; i * i <= n; i += 2) {
            // 2a. Find the exponent of the factor i
            int count = 0;
            while (n % i == 0) {
                count++;
                n /= i;
            }

            // 2b. Check the condition from Fermat's theorem
            // If i is of form 4k+3 (i % 4 == 3) and has an odd exponent
            if (i % 4 == 3 && count % 2 != 0) {
                LOGGER.debug("Failing condition: factor {} (form 4k+3) has odd exponent {}", i, count);
                return false;
            }
        }

        // 3. Handle the last remaining factor (which is prime if > 1)
        // If n itself is a prime of the form 4k+3, its exponent is 1 (odd).
        if (n % 4 == 3) {
            LOGGER.debug("Failing condition: remaining factor {} is of form 4k+3", n);
            return false;
        }

        // 4. All 4k+3 primes had even exponents.
        return true;
    }
}