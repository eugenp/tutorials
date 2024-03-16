package com.baeldung.matrixtozero;

import org.junit.Test;

public class SetMatrixToZeroUnitTest{
    @Test 
    void givenMatrix_whenUsingBruteForce_thenSetZeroes() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 9}
        };
        int[][] expected = {
                {1, 0, 3},
                {0, 0, 0},
                {7, 0, 9}
        };
        SetMatrixToZero.setZeroesBruteForce(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    void givenMatrix_whenUsingExtraSpace_thenSetZeroes() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 9}
        };
        int[][] expected = {
                {1, 0, 3},
                {0, 0, 0},
                {7, 0, 9}
        };
        SetMatrixToZero.setZeroesExtraSpace(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    void givenMatrix_whenUsingOptimized_thenSetZeroes() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 0, 6},
                {7, 8, 9}
        };
        int[][] expected = {
                {1, 0, 3},
                {0, 0, 0},
                {7, 0, 9}
        };
        SetMatrixToZero.setZeroesOptimized(matrix);
        assertArrayEquals(expected, matrix);
    }
}
