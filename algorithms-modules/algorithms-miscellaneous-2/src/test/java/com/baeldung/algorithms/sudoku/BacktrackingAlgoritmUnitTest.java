package com.baeldung.algorithms.sudoku;

import org.junit.Assert;
import org.junit.Test;

public class BacktrackingAlgoritmUnitTest {

    private static final int[][] board = {
        {8, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 3, 6, 0, 0, 0, 0, 0},
        {0, 7, 0, 0, 9, 0, 2, 0, 0},
        {0, 5, 0, 0, 0, 7, 0, 0, 0},
        {0, 0, 0, 0, 4, 5, 7, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 3, 0},
        {0, 0, 1, 0, 0, 0, 0, 6, 8},
        {0, 0, 8, 5, 0, 0, 0, 1, 0},
        {0, 9, 0, 0, 0, 0, 4, 0, 0}
    };

    @Test
    public void testSudokuBacktrackingAlgorithm() {
        BacktrackingAlgorithm solver = new BacktrackingAlgorithm();
        solver.solve(board);
        Assert.assertArrayEquals(board[0], new int[] {8,1,2,7,5,3,6,4,9});
    }

}
