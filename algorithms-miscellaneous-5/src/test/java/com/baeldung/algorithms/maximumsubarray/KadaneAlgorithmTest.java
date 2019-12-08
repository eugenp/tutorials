package com.baeldung.algorithms.maximumsubarray;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KadaneAlgorithmTest {

    @Test
    void givenArrayWithNegativeNumberWhenMaximumSubarrayThenReturns6() {
        //given
        int[] arr = new int[]{-3, 1, -8, 4, -1, 2, 1, -5, 5};
        //when
        KadaneAlgorithm algorithm = new KadaneAlgorithm();
        int maxSum = algorithm.maxSubArraySum(arr);
        //then
        assertEquals(6, maxSum);
    }
}