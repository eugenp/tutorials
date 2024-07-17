package com.baeldung.algorithms.subarray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SubarrayMeanPrefixSumsUnitTest {

    @Test
    void givenMultipleTestCases_thenReturnTheCorrectCount() {
        int[][] testCases = {
            {5, 3, 6, 2},
            {1, 1, 1},
            {2, 2, 2, 2},
            {1, 2, 3, 4},
            {},
        };
        int[] targets = {4, 1, 3, 2, 0};
        int[] expected = {3, 6, 0, 2, 0};

        for (int i = 0; i < testCases.length; i++) {
            int actual = SubarrayMeansPrefixSums.countSubarraysWithMean(testCases[i], targets[i]);
            assertEquals(expected[i], actual, "Test case " + (i + 1));
        }
    }

    @Test
    void givenSingleValueWithSameTargetMean_whenMeanIs5_thenReturn1() {
        int[] arr = {5};
        assertEquals(1, SubarrayMeansPrefixSums.countSubarraysWithMean(arr, 5));
        assertEquals(0, SubarrayMeansPrefixSums.countSubarraysWithMean(arr, 0));
    }

    @Test
    void givenMultipleZeros_thenReturnPermutationCount() {
        int[] arr = {0, 0, 0, 0};
        assertEquals(10, SubarrayMeansPrefixSums.countSubarraysWithMean(arr, 0));
        assertEquals(0, SubarrayMeansPrefixSums.countSubarraysWithMean(arr, 1));
    }

    @Test
    void givenLargeNumbers_thenCountCorrectly() {
        int[] arr = {1_000_000_000, 2_000_000_000};
        assertEquals(1, SubarrayMeansPrefixSums.countSubarraysWithMean(arr, 1_500_000_000));
    }
}
