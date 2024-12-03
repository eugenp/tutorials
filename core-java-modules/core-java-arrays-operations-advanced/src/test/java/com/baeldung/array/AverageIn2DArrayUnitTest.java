package com.baeldung.array;

import org.junit.Assert;
import org.junit.Test;

import java.util.OptionalDouble;

public class AverageIn2DArrayUnitTest {

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
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(singleElementArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenPositiveNumbersArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(positiveNumbersArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenMixedNumbersArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 0.222;
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(mixedNumbersArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.001);
    }

    @Test
    public void givenZerosArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 0.0;
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(zerosArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenEmptyArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = Double.NaN;
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(emptyArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenEmptyRowsArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = Double.NaN;
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(emptyRowsArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    @Test
    public void givenIrregularArray_whenNotUsingStream_thenFindAverage() {
        double expectedAverage = 3.5;
        double actualAverage = AverageIn2DArray.findAverageWithoutUsingStream(irregularArray);
        Assert.assertEquals(expectedAverage, actualAverage, 0.0001);
    }

    // Using Stream

    @Test
    public void givenSingleElementArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(singleElementArray);
        Assert.assertTrue("Expected average should be present", actualAverage.isPresent());
        Assert.assertEquals(expectedAverage, actualAverage.getAsDouble(), 0.0001);
    }

    @Test
    public void givenPositiveNumbersArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 5.0;
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(positiveNumbersArray);
        Assert.assertTrue("Expected average should be present", actualAverage.isPresent());
        Assert.assertEquals(expectedAverage, actualAverage.getAsDouble(), 0.0001);
    }

    @Test
    public void givenMixedNumbersArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 0.222;
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(mixedNumbersArray);
        Assert.assertTrue("Expected average should be present", actualAverage.isPresent());
        Assert.assertEquals(expectedAverage, actualAverage.getAsDouble(), 0.001);
    }

    @Test
    public void givenZerosArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 0.0;
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(zerosArray);
        Assert.assertTrue("Expected average should be present", actualAverage.isPresent());
        Assert.assertEquals(expectedAverage, actualAverage.getAsDouble(), 0.0001);
    }

    @Test
    public void givenEmptyArray_whenUsingStream_thenFindAverage() {
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(emptyArray);
        Assert.assertFalse("Expected no average to be present", actualAverage.isPresent());
    }

    @Test
    public void givenEmptyRowsArray_whenUsingStream_thenFindAverage() {
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(emptyRowsArray);
        Assert.assertFalse("Expected no average to be present", actualAverage.isPresent());
    }

    @Test
    public void givenIrregularArray_whenUsingStream_thenFindAverage() {
        double expectedAverage = 3.5;
        OptionalDouble actualAverage = AverageIn2DArray.findAverageUsingStream(irregularArray);
        Assert.assertTrue("Expected average should be present", actualAverage.isPresent());
        Assert.assertEquals(expectedAverage, actualAverage.getAsDouble(), 0.0001);
    }
}
