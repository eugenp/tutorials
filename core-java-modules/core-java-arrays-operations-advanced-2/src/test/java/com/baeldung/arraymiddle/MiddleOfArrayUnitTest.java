package com.baeldung.arraymiddle;

import org.junit.Assert;
import org.junit.Test;

public class MiddleOfArrayUnitTest {

    @Test
    public void givenArrayOfEvenLength_whenMiddleOfArray_thenReturn2Values() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int[] expectedMidArray = { 50, 51 };
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArray(array));
    }

    @Test
    public void givenArrayOfEdgeCaseLength_whenMiddleOfArray_thenReturn2Values() {
        int[] array = new int[0];
        int[] expectedMidArray = new int[0];
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArray(array));

        array = new int[] { 1, 2 };
        expectedMidArray = new int[] { 1, 2 };

        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArray(array));
    }

    @Test
    public void givenArrayOfOddLength_whenMiddleOfArray_thenReturnMid() {
        int[] array = new int[99];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int[] expectedMidArray = { 50 };
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArray(array));
    }

    @Test
    public void givenArrayWithStartAndEnd_whenMiddleOfArray_thenReturnMid() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int[] expectedMidArray = { 58 };
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArrayWithStartEndNaive(array, 55, 60));

        expectedMidArray = new int[] { 58, 59 };
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArrayWithStartEndNaive(array, 56, 60));
    }

    @Test
    public void givenArrayWithStartAndEndOptimized_whenMiddleOfArray_thenReturnMid() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int[] expectedMidArray = { 78 };
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArrayWithStartEnd(array, 55, 100));
    }

    @Test
    public void givenArrayWithStartAndEndBitwise_whenMiddleOfArray_thenReturnMid() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int[] expectedMidArray = { 78 };
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertArrayEquals(expectedMidArray, middleOfArray.middleOfArrayWithStartEndBitwise(array, 55, 100));
    }

    @Test
    public void givenArrayWithStartAndEnd_whenMedianOfArray_thenReturnMid() {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
        int expectMedian = 50;
        MiddleOfArray middleOfArray = new MiddleOfArray();
        Assert.assertEquals(expectMedian, middleOfArray.medianOfArray(array, 0, 100));
    }
}