package com.baeldung.array.comparing2Darrays;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class Comparing2DArraysUnitTest {

    int[][] arr1 = { { 1, 2, 3 }, { 4, 5, 6 } };
    int[][] arr2 = { { 1, 2, 3 }, { 4, 5, 6 } };

    @Test
    public void whenComparing22DArraysNaively_thenItIsLessEfficient(){
        Assertions.assertTrue(NaiveApproach.areArraysEqual(arr1, arr2));
    }

    @Test
    public void whenComparing22DArraysOptimized_thenItIsMoreEfficient(){
        Assertions.assertTrue(OptimizedApproach.areArraysEqual(arr1, arr2, 0.9, 1.0));
    }
}
