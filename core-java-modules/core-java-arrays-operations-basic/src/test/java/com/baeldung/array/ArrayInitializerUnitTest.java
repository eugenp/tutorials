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
import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

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
}
