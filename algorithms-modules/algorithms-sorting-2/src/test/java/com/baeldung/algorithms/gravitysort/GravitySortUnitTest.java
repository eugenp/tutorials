package com.baeldung.algorithms.gravitysort;

import org.junit.Assert;
import org.junit.Test;

public class GravitySortUnitTest {

    @Test
    public void givenIntegerArray_whenSortedWithGravitySort_thenGetSortedArray() {
        int[] actual = { 9, 9, 100, 3, 57, 12, 3, 78, 0, 2, 2, 40, 21, 9 };
        int[] expected = { 0, 2, 2, 3, 3, 9, 9, 9, 12, 21, 40, 57, 78, 100 };
        GravitySort.sort(actual);
        Assert.assertArrayEquals(expected, actual);
    }
}
