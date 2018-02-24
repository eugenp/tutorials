package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

public class SumAndAverageInArrayTest {
    @Test
    public void whenGivenAnIntArrayFindSum() {
        int[] array = {1, 3, 4, 8, 19, 20};
        int expectedSumOfArray = 55;
        int actualSumOfArray = 0;

        for (int index = 0; index < array.length; index++) {
            actualSumOfArray += array[index];
        }
        Assert.assertEquals(expectedSumOfArray, actualSumOfArray);
    }

    @Test
    public void whenGivenAnIntArrayFindAverage() {
        int[] array = {1, 3, 4, 8, 19, 20};
        double expectedAvgOfArray = 9.166;

        int sumOfArray = 0;
        double actualAvgOfArray = 0;

        for (int index = 0; index < array.length; index++) {
            sumOfArray += array[index];
        }
        actualAvgOfArray = ((double) sumOfArray) / array.length;
        Assert.assertEquals(expectedAvgOfArray, expectedAvgOfArray, 0.00f);
    }


}
