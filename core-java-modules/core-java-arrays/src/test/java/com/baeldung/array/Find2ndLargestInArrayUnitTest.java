package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

public class Find2ndLargestInArrayUnitTest {
    @Test
    public void givenAnIntArray_thenFind2ndLargestElement() {
        int[] array = { 1, 3, 24, 16, 87, 20 };
        int expected2ndLargest = 24;

        int actualSecondLargestElement = Find2ndLargestInArray.find2ndLargestElement(array);

        Assert.assertEquals(expected2ndLargest, actualSecondLargestElement);
    }
}
