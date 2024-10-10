package com.baeldung.sumofnevendivisibleby3;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SumOfNFirstEvenNumbersDivisibleBy3UnitTest {

    private static final int NUMBER = 7;
    private static final int EXPECTED_SUM = 18;

    @Test
    void givenN_whenUsingBruteForceForLoop_thenReturnsCorrectSum() {
        int sum = 0;

        for (int i = 2; i <= NUMBER * 2; i++) {
            if (i % 2 == 0 && i % 3 == 0) {
                sum += i;
            }
        }

        assertEquals(EXPECTED_SUM, sum);
    }

    @Test
    void givenN_whenUsingFunctionalApproach_thenReturnsCorrectSum() {
        int sum = IntStream.iterate(2, i -> i + 1)
          .filter(i -> i % 2 == 0)
          .limit(NUMBER)
          .filter(i -> i % 3 == 0)
          .sum();

        assertEquals(EXPECTED_SUM, sum);
    }

    @Test
    void givenN_whenUsingImprovedBruteForce_thenReturnsCorrectSum() {
        int sum = IntStream.iterate(6, i -> i + 6)
          .limit(NUMBER / 3)
          .sum();

        assertEquals(EXPECTED_SUM, sum);
    }

    @Test
    void givenN_whenUsingOptimizedMethod_thenReturnsCorrectSum() {
        int sum = 3 * (NUMBER / 3) * (NUMBER / 3 + 1);

        assertEquals(EXPECTED_SUM, sum);
    }

}
