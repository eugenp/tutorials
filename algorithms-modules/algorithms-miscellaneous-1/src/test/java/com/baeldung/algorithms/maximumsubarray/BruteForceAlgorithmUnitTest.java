package com.baeldung.algorithms.maximumsubarray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BruteForceAlgorithmUnitTest {

    @Test
    void givenArrayWithNegativeNumberWhenMaximumSubarrayThenReturns6() {
        //given
        int[] arr = new int[]{-3, 1, -8, 4, -1, 2, 1, -5, 5};
        //when
        BruteForceAlgorithm algorithm = new BruteForceAlgorithm();
        int maximumSum = algorithm.maxSubArray(arr);
        //then
        assertEquals(6, maximumSum);
    }
}