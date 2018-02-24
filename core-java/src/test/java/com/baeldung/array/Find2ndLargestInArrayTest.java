package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Raghav Jha on 24/02/18 at 8:31 PM.
 */
public class Find2ndLargestInArrayTest {
    @Test
    public void whenGivenAnIntArrayFindAn2ndLargestElement() {
        int[] array = {1, 3, 24, 16, 87, 20};
        int expected2ndLargest = 24;

        int maxElement = array[0];
        int secondLargestElement = -1;

        for (int index = 0; index < array.length; index++) {
            if (maxElement <= array[index]) {
                secondLargestElement = maxElement;
                maxElement = array[index];
            } else if (secondLargestElement < array[index]) {
                secondLargestElement = array[index];
            }
        }
        Assert.assertEquals(expected2ndLargest, secondLargestElement);
    }
}
