package com.baeldung.arrayindex;

import static com.baeldung.arrayindex.ArrayIndex.forLoop;
import static com.baeldung.arrayindex.ArrayIndex.intStream;
import static com.baeldung.arrayindex.ArrayIndex.listIndexOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import com.google.common.primitives.Ints;

class ArrayIndexUnitTest {

    @Test
    void givenIntegerArray_whenUseForLoop_thenWillGetElementIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(2, forLoop(numbers, 30));
    }

    @Test
    void givenIntegerArray_whenUseForLoop_thenWillGetElementMinusOneIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, forLoop(numbers, 100));
    }

    @Test
    void givenIntegerArray_whenUseIndexOf_thenWillGetElementIndex() {
        Integer[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(2, listIndexOf(numbers, 30));
    }

    @Test
    void givenIntegerArray_whenUseIndexOf_thenWillGetElementMinusOneIndex() {
        Integer[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, listIndexOf(numbers, 100));
    }

    @Test
    void givenIntegerArray_whenUseIntStream_thenWillGetElementIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(2, intStream(numbers, 30));
    }

    @Test
    void givenIntegerArray_whenUseIntStream_thenWillGetElementMinusOneIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, intStream(numbers, 100));
    }

    @Test
    void givenIntegerArray_whenUseBinarySearch_thenWillGetElementIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(2, Arrays.binarySearch(numbers, 30));
    }

    @Test
    void givenIntegerArray_whenUseBinarySearch_thenWillGetUpperBoundMinusIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-6, Arrays.binarySearch(numbers, 100));
    }

    @Test
    void givenIntegerArray_whenUseBinarySearch_thenWillGetInArrayMinusIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-2, Arrays.binarySearch(numbers, 15));
    }

    @Test
    void givenIntegerArray_whenUseBinarySearch_thenWillGetLowerBoundMinusIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, Arrays.binarySearch(numbers, -15));
    }

    @Test
    void givenIntegerArray_whenUseApacheCommons_thenWillGetElementIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(2, ArrayUtils.indexOf(numbers, 30));
    }

    @Test
    void givenIntegerArray_whenUseApacheCommonsStartingFromIndex_thenWillGetNegativeIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, ArrayUtils.indexOf(numbers, 30, 3));
    }

    @Test
    void givenIntegerArray_whenUseApacheCommons_thenWillGetElementMinusOneIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, ArrayUtils.indexOf(numbers, 100));
    }

    @Test
    void givenIntegerArray_whenUseGuavaInts_thenWillGetElementIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(2, Ints.indexOf(numbers, 30));
    }

    @Test
    void givenIntegerArray_whenUseGuavaInts_thenWillGetElementMinusOneIndex() {
        int[] numbers = { 10, 20, 30, 40, 50 };
        assertEquals(-1, Ints.indexOf(numbers, 100));
    }
}