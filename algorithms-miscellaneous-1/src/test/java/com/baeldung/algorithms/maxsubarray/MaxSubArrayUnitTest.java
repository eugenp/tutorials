package com.baeldung.algorithms.maxsubarray;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class MaxSubArrayUnitTest {

    @Test
    public void givenIntegerArray_whenFindMaxSubArraySum_thenGetMaxSubArraySum() {
        int[] input = { -1, 0, 5, 1, 4, -2 };
        int expected = 10;
        int actual = MaxSubArray.findMaxSubArraySum(input, 0, input.length - 1);
        assertEquals(expected, actual);
    }

}
