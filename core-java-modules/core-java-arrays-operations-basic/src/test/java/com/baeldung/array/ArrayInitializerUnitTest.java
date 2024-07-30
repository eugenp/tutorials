package com.baeldung.array;

import static com.baeldung.array.ArrayInitializer.initializeArrayAtTimeOfDeclarationMethod1;
import static com.baeldung.array.ArrayInitializer.initializeArrayAtTimeOfDeclarationMethod2;
import static com.baeldung.array.ArrayInitializer.initializeArrayAtTimeOfDeclarationMethod3;
import static com.baeldung.array.ArrayInitializer.initializeArrayInLoop;
import static com.baeldung.array.ArrayInitializer.initializeArrayRangeUsingArraysFill;
import static com.baeldung.array.ArrayInitializer.initializeArrayUsingArraysCopy;
import static com.baeldung.array.ArrayInitializer.initializeArrayUsingArraysFill;
import static com.baeldung.array.ArrayInitializer.initializeArrayUsingArraysSetAll;
import static com.baeldung.array.ArrayInitializer.initializeArrayUsingArraysUtilClone;
import static com.baeldung.array.ArrayInitializer.initializeLargerArrayUsingArraysCopy;
import static com.baeldung.array.ArrayInitializer.initializeMultiDimensionalArrayInLoop;
import static com.baeldung.array.ArrayInitializer.initializeTwoDimensionalArrayWithDefaultValues;
import static com.baeldung.array.ArrayInitializer.initializeArrayWithDefaultValues;
import static com.baeldung.array.ArrayInitializer.initializeArrayUsingArraysStream;
import static com.baeldung.array.ArrayInitializer.initializeArrayUsingIntStream;
import static com.baeldung.array.ArrayInitializer.initializeArrayOfDoubleTypeUsingStreamApi;
import static com.baeldung.array.ArrayInitializer.initializeTwoDimensionalArrayUsingStream;
import static com.baeldung.array.ArrayInitializer.resizeArrayUsingSystemCopyArray;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

public class ArrayInitializerUnitTest {

    @Test
    public void whenInitializeArrayInLoop_thenCorrect() {
        assertArrayEquals(new int[] { 2, 3, 4, 5, 6 }, initializeArrayInLoop());
    }

    @Test
    public void whenInitializeMultiDimensionalArrayInLoop_thenCorrect() {
        assertArrayEquals(new int[][] { { 1, 2, 3, 4, 5 }, { 1, 2, 3, 4, 5 } }, initializeMultiDimensionalArrayInLoop());
    }

    @Test
    public void whenInitializeArrayAtTimeOfDeclarationMethod1_thenCorrect() {
        assertArrayEquals(new String[] { "Toyota", "Mercedes", "BMW", "Volkswagen", "Skoda" }, initializeArrayAtTimeOfDeclarationMethod1());
    }

    @Test
    public void whenInitializeArrayAtTimeOfDeclarationMethod2_thenCorrect() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, initializeArrayAtTimeOfDeclarationMethod2());
    }

    @Test
    public void whenInitializeArrayAtTimeOfDeclarationMethod3_thenCorrect() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, initializeArrayAtTimeOfDeclarationMethod3());
    }

    @Test
    public void whenInitializeArrayUsingArraysFill_thenCorrect() {
        assertArrayEquals(new long[] { 30, 30, 30, 30, 30 }, initializeArrayUsingArraysFill());
    }

    @Test
    public void whenInitializeArrayRangeUsingArraysFill_thenCorrect() {
        assertArrayEquals(new int[] { -50, -50, -50, 0, 0 }, initializeArrayRangeUsingArraysFill());
    }

    @Test
    public void whenInitializeArrayRangeUsingArraysCopy_thenCorrect() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, initializeArrayUsingArraysCopy());
    }

    @Test
    public void whenInitializeLargerArrayRangeUsingArraysCopy_thenCorrect() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 0 }, initializeLargerArrayUsingArraysCopy());
    }

    @Test
    public void whenInitializeLargerArrayRangeUsingArraysSetAll_thenCorrect() {
        assertArrayEquals(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, initializeArrayUsingArraysSetAll());
    }

    @Test
    public void whenInitializeArrayUsingArraysUtilClone_thenCorrect() {
        assertArrayEquals(new char[] { 'a', 'b', 'c' }, initializeArrayUsingArraysUtilClone());
    }

    @Test
    public void whenInitializeArrayWithDefault_thenCorrect() {
        assertArrayEquals(new int[5], initializeArrayWithDefaultValues());
        int[] array = new int[5];
        assertArrayEquals(new int[] { 0, 0, 0, 0, 0 }, array);
    }

    @Test
    public void whenInitializeArrayOfBooleanTypeWithDefault_thenCorrect() {
        boolean[] array = new boolean[5];
        assertArrayEquals(new boolean[] { false, false, false, false, false }, array);
    }

    @Test
    public void whenInitializeArrayOfStringTypeWithDefault_thenCorrect() {
        String[] array = new String[5];
        assertArrayEquals(new String[] { null, null, null, null, null }, array);
    }

    @Test
    public void whenAddingItemsToAnArray_thenCorrect() {
        int[] numbers = initializeArrayWithDefaultValues();
        numbers[0] = 10;
        numbers[1] = 20;
        numbers[2] = 30;
        numbers[3] = 40;
        numbers[4] = 50;
        assertEquals(5, numbers.length);
        assertEquals(20, numbers[1]);
    }

    @Test
    public void whenInitializeEmptyTwoDimensionalArray_thenCorrect() {
        assertArrayEquals(new int[2][5], initializeTwoDimensionalArrayWithDefaultValues());
    }

    @Test
    public void whenAddingItemsToAnTwoDimensionalArray_thenCorrect() {
        int[][] matrix = initializeTwoDimensionalArrayWithDefaultValues();
        matrix[0][0] = 10;
        matrix[0][1] = 20;
        matrix[0][2] = 30;
        matrix[0][3] = 40;
        matrix[0][4] = 50;
        matrix[1][0] = 60;
        matrix[1][1] = 70;
        matrix[1][2] = 80;
        matrix[1][3] = 90;
        matrix[1][4] = 100;
        assertEquals(2, matrix.length);
        assertEquals(5, matrix[0].length);
        assertEquals(5, matrix[1].length);
        assertEquals(20, matrix[0][1]);
    }

    @Test
    public void whenInitializeArrayWithStream_thenCorrect() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, initializeArrayUsingArraysStream());
    }

    @Test
    public void whenInitializeArrayOfIntegerUsingIntStream_thenCorrect() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, initializeArrayUsingIntStream());
    }

    @Test
    public void whenInitializeArrayOfDoubleUsingStreamApi_thenCorrect() {
        assertArrayEquals(new double[] { 1.1, 2.2, 3.3, 4.4, 5.5 }, initializeArrayOfDoubleTypeUsingStreamApi(), 0.0);
    }

    @Test
    public void whenInitializeEmptyThreeDimensionalArray_thenCorrect() {
        int[][][] threeDimensionalArray = new int[2][3][4];
        assertEquals(2, threeDimensionalArray.length);
        assertEquals(3, threeDimensionalArray[0].length);
        assertEquals(4, threeDimensionalArray[0][0].length);
    }

    @Test
    public void whenInitializeTwoDimensionalArrayWithStreamApi_thenCorrect() {
        assertArrayEquals(new int[][] { { 0, 1, 2, 3 }, { 4, 5, 6, 7 }, { 8, 9, 10, 11 } }, initializeTwoDimensionalArrayUsingStream());
    }

    @Test
    public void whenPopulatingAnArrayWithSetAllMethod_thenCorrect() {
        int[] numbers = new int[5];
        Arrays.setAll(numbers, i -> i * 2);
        assertArrayEquals(new int[] { 0, 2, 4, 6, 8 }, numbers);
    }

    @Test
    public void whenResizeArrayUsingSystemCopyArray_thenCorrect() {
        assertEquals(10, resizeArrayUsingSystemCopyArray().length);
        assertArrayEquals(new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 0, 0 }, resizeArrayUsingSystemCopyArray());
    }
}
