package com.baeldung.matrixtozero;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class SetMatrixToZeroUnitTest{
    @Test 
    void givenMatrix_whenUsingSetZeroesByNaiveApproach_thenSetZeroes() {
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
        SetMatrixToZero.setZeroesByNaiveApproach(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    void givenMatrix_whenUsingSetZeroesByTimeOptimizedApproach_thenSetZeroes() {
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
        SetMatrixToZero.setZeroesByTimeOptimizedApproach(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    void givenMatrix_whenUsingSetZeroesByOptimalApproach_thenSetZeroes() {
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
        SetMatrixToZero.setZeroesByOptimalApproach(matrix);
        assertArrayEquals(expected, matrix);
    }
}
