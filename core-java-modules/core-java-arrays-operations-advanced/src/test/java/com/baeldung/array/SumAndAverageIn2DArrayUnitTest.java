package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

public class SumAndAverageIn2DArrayUnitTest {

    private static final int[][] singleElementArray = {{5}};
    private static final int[][] positiveNumbersArray = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    };
    private static final int[][] mixedNumbersArray = {
            {-3, 4, -2},
            {1, -5, 6},
            {2, 0, -1}
    };
    private static final int[][] zerosArray = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };
    private static final int[][] emptyArray = {};
    private static final int[][] emptyRowsArray = {{}};
    private static final int[][] irregularArray = {
            {1, 2, 3},
            {4, 5},
            {6}
    };


    // Without Stream
    @Test
    public void givenSingleElementArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(singleElementArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenPositiveNumbersArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(positiveNumbersArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenMixedNumbersArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 0.222;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(mixedNumbersArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void givenZerosArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 0.0;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(zerosArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenEmptyArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = Double.NaN;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(emptyArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenEmptyRowsArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = Double.NaN;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(emptyRowsArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenIrregularArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 3.5;
        double actualAverage = SumAndAverageIn2DArray.findAverageWithoutUsingStream(irregularArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    // Using Stream

    @Test
    public void givenSingleElementArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(singleElementArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenPositiveNumbersArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(positiveNumbersArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenMixedNumbersArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 0.222;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(mixedNumbersArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void givenZerosArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 0.0;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(zerosArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenEmptyArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = Double.NaN;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(emptyArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenEmptyRowsArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = Double.NaN;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(emptyRowsArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenIrregularArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 3.5;
        double actualAverage = SumAndAverageIn2DArray.findAverageUsingStream(irregularArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }
}
