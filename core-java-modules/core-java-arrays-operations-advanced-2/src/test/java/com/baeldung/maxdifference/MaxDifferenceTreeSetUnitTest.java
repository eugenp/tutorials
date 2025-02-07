package com.baeldung.maxdifference;

import static org.junit.Assert.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class MaxDifferenceTreeSetUnitTest {

    @Test
    public void givenAnArray_whenUsingTreeSet_thenReturnCorrectMaxDifferenceInformation() {
        int[] list = { 3, -10, 7, 1, 5, -3, 10, -2, 6, 0 };
        int[] result = MaxDifferenceTreeSet.findMaxDifferenceTreeSet(list);
        assertArrayEquals(new int[] { 1, 6, -10, 10, 20 }, result);
    }
}
