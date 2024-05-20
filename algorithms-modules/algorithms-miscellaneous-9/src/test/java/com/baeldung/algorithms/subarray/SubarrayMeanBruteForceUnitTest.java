package com.baeldung.algorithms.subarray;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SubarrayMeanBruteForceUnitTest {

      @Test
    void givenMultipleTestCasesReturnTheCorrectCount() {
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
            int actual = SubarrayMeansBruteForce.countSubarraysWithMean(testCases[i], targets[i]);
            assertEquals(expected[i], actual, "Test case " + (i + 1));
        }
    }

    @Test
    void givenSingleValueWithSameTargetMeanShouldCountCorrectly() {
        int[] arr = {5};
        assertEquals(1, SubarrayMeansBruteForce.countSubarraysWithMean(arr, 5));
        assertEquals(0, SubarrayMeansBruteForce.countSubarraysWithMean(arr, 0));
    }

    @Test
    void givenMultipleZerosReturnPermutationCount() {
        int[] arr = {0, 0, 0, 0};
        assertEquals(10, SubarrayMeansBruteForce.countSubarraysWithMean(arr, 0));
        assertEquals(0, SubarrayMeansBruteForce.countSubarraysWithMean(arr, 1));
    }

    @Test
    void givenLargeNumbersReturnCountCorrectly() {
        int[] arr = {1_000_000_000, 2_000_000_000};
        assertEquals(1, SubarrayMeansBruteForce.countSubarraysWithMean(arr, 1_500_000_000));
    }
}