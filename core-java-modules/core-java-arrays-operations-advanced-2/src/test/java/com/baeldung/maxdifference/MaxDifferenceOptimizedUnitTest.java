package com.baeldung.maxdifference;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class MaxDifferenceOptimizedUnitTest {

    @Test
    public void givenAnArray_whenUsingOptimizedOnePass_thenReturnCorrectMaxDifferenceInformation() {
        int[] list = { 3, -10, 7, 1, 5, -3, 10, -2, 6, 0 };
        int[] result = MaxDifferenceOptimized.findMaxDifferenceOptimized(list);
        assertArrayEquals(new int[] { 1, 6, -10, 10, 20 }, result);
    }
}
