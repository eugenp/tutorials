package com.baeldung.algorithms.quicksort;

import org.junit.Assert;
import org.junit.Test;

public class BentleyMcilroyPartitioningUnitTest {

    @Test
    public void given_IntegerArray_whenSortedWithBentleyMcilroyPartitioning_thenGetSortedArray() {
        int[] actual = {3, 2, 2, 2, 3, 7, 7, 3, 2, 2, 7, 3, 3};
        int[] expected = {2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 7, 7, 7};
        BentleyMcIlroyPartioning.quicksort(actual, 0, actual.length - 1);
        Assert.assertArrayEquals(expected, actual);
    }

}
