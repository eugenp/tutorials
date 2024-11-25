package com.baeldung.maxdifference;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class MaxDifferenceBruteForceUnitTest {

    @Test
    public void givenAnArray_whenUsingBruteForce_thenReturnCorrectMaxDifferenceInformation() {
        int[] list = { 3, -10, 7, 1, 5, -3, 10, -2, 6, 0 };
        int[] result = MaxDifferenceBruteForce.findMaxDifferenceBruteForce(list);
        assertArrayEquals(new int[] { 1, 6, -10, 10, 20 }, result);
    }
}
