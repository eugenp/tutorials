package com.baeldung.algorithms.consecutivenumbers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConsecutiveNumbersUnitTest {

    @Test
    void whenIsSumOfConsecutiveUsingBruteForce_thenReturnsTrue() {
        int n = 15;

        boolean isSumOfConsecutive = false;
        for (int k = 2; (k * (k - 1)) / 2 < n; k++) {
            int diff = n - k * (k - 1) / 2;
            if (diff % k == 0 && diff / k > 0) {
                isSumOfConsecutive = true;
                break;
            }
        }

        assertTrue(isSumOfConsecutive);
    }

    @Test
    void whenIsSumOfConsecutiveUsingBitwise_thenReturnsTrue() {
        int n = 15;
        boolean result = (n > 0) && ((n & (n - 1)) != 0);
        assertTrue(result);
    }

}
