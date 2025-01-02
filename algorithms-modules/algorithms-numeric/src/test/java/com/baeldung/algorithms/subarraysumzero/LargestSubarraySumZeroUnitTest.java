package com.baeldung.algorithms.subarraysumzero;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LargestSubarraySumZeroUnitTest {

    @Test
    public void givenArray_whenUseTwoLoops_thenFindSubarrayWithSumZero() {
        int[] arr1 = { 4, -3, -6, 5, 1, 6, 8 };
        int[] arr2 = { 15, -2, 2, -8, 1, 7, 10, 23 };
        int[] arr3 = { -3, 2, 3, 1, 6 };

        assertEquals(4, LargestSubarraySumZero.maxLenBruteForce(arr1));
        assertEquals(5, LargestSubarraySumZero.maxLenBruteForce(arr2));
        assertEquals(0, LargestSubarraySumZero.maxLenBruteForce(arr3));
    }

    @Test
    public void givenArray_whenUseHashmap_thenFindSubarrayWithSumZero() {
        int[] arr1 = { 4, -3, -6, 5, 1, 6, 8 };
        int[] arr2 = { 15, -2, 2, -8, 1, 7, 10, 23 };
        int[] arr3 = { -3, 2, 3, 1, 6 };

        assertEquals(4, LargestSubarraySumZero.maxLenHashMap(arr1));
        assertEquals(5, LargestSubarraySumZero.maxLenHashMap(arr2));
        assertEquals(0, LargestSubarraySumZero.maxLenHashMap(arr3));
    }
}
