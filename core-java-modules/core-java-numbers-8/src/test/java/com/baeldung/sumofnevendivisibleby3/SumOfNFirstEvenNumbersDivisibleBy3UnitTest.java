package com.baeldung.sumofnevendivisibleby3;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumOfNFirstEvenNumbersDivisibleBy3UnitTest {

    private final int N = 5;
    private final int EXPECTED_SUM = 90;

    @Test
    public void givenN_whenUsingBruteForceForLoop_thenReturnsCorrectSum() {
        int sum = 0;
        int count = 0;

        for (int i = 2; count < N; i++) {
            if (i % 2 == 0 && i % 3 == 0) {
                sum += i;
                count++;
            }
        }

        assertEquals(EXPECTED_SUM, sum);
    }

    @Test
    public void givenN_whenUsingFunctionalApproach_thenReturnsCorrectSum() {

        int sum = IntStream.iterate(2, i -> i + 1)
          .filter(i -> i % 2 == 0 && i % 3 == 0)
          .limit(N)
          .sum();

        assertEquals(EXPECTED_SUM, sum);
    }

    @Test
    public void givenN_whenUsingImprovedBruteForce_thenReturnsCorrectSum() {

        int sum = IntStream.iterate(6, i -> i + 6)
          .limit(N)
          .sum();

        assertEquals(EXPECTED_SUM, sum);
    }

    @Test
    public void givenN_whenUsingOptimizedMethod_thenReturnsCorrectSum() {
        int sum = 3 * (N * (N + 1));

        assertEquals(EXPECTED_SUM, sum);
    }

}
