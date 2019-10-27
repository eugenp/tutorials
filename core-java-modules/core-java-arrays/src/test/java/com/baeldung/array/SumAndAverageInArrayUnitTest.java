package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

public class SumAndAverageInArrayUnitTest {
    @Test
    public void givenAnIntArray_whenNotUsingStream_thenFindSum() {
        int[] array = { 1, 3, 4, 8, 19, 20 };
        int expectedSumOfArray = 55;
        int actualSumOfArray = SumAndAverageInArray.findSumWithoutUsingStream(array);
        Assert.assertEquals(expectedSumOfArray, actualSumOfArray);
    }

    @Test
    public void givenAnIntArray_whenUsingStream_thenFindSum() {
        int[] array = { 1, 3, 4, 8, 19, 20 };
        int expectedSumOfArray = 55;
        int actualSumOfArray = SumAndAverageInArray.findSumUsingStream(array);

        Assert.assertEquals(expectedSumOfArray, actualSumOfArray);
    }

    @Test
    public void givenAnBoxedIntegerArray_whenUsingStream_thenFindSum() {
        Integer[] array = new Integer[]{1, 3, 4, 8, 19, 20};
        int expectedSumOfArray = 55;
        int actualSumOfArray = SumAndAverageInArray.findSumUsingStream(array);

        Assert.assertEquals(expectedSumOfArray, actualSumOfArray);
    }

    @Test
    public void givenAnIntArray_whenNotUsingStream_thenFindAverage() {
        int[] array = { 1, 3, 4, 8, 19, 20 };
        double expectedAvgOfArray = 9.17;
        double actualAvgOfArray = SumAndAverageInArray.findAverageWithoutUsingStream(array);

        Assert.assertEquals(expectedAvgOfArray, actualAvgOfArray, 0.0034);
    }

    @Test
    public void givenAnIntArray_whenUsingStream_thenFindAverage() {
        int[] array = { 1, 3, 4, 8, 19, 20 };
        double expectedAvgOfArray = 9.17;
        double actualAvgOfArray = SumAndAverageInArray.findAverageUsingStream(array);

        Assert.assertEquals(expectedAvgOfArray, actualAvgOfArray, 0.0034);
    }

    @Test
    public void givenAnEmptyIntArray_whenUsingStream_thenFindAverage() {
        int[] array = {};
        double expectedAvgOfArray = Double.NaN;
        double actualAvgOfArray = SumAndAverageInArray.findAverageUsingStream(array);

        Assert.assertEquals(expectedAvgOfArray, actualAvgOfArray, 0.00);
    }
}
