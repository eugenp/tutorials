package com.baeldung.algorithms.subarray.maximum;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KadaneAlgorithmUnitTest {

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