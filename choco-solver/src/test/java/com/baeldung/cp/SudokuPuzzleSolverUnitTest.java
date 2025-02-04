package com.baeldung.cp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.chocosolver.util.tools.ArrayUtils;
import org.junit.jupiter.api.Test;

public class SudokuPuzzleSolverUnitTest {

    static final int[][] initialValues = {
        {0, 0, 0, 0, 0, 0, 0, 6, 3},
        {5, 0, 0, 4, 0, 0, 8, 0, 0},
        {1, 0, 0, 0, 3, 0, 0, 4, 0},
        {2, 0, 0, 8, 0, 0, 9, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0, 7, 4, 0, 0},
        {0, 0, 6, 3, 0, 0, 0, 0, 0},
        {0, 0, 9, 2, 0, 0, 1, 0, 0},
        {0, 0, 7, 0, 8, 0, 6, 0, 9}
    };

    @Test
    void whenSudokuBoardIsEmpty_thenFindTwoSolutions() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        List<Integer[][]> sudokuSolutionMatrices = sudokuSolver.findSolutions(2);

        sudokuSolutionMatrices.forEach(sudokuSolver::printSolution);
        sudokuSolutionMatrices.forEach(e -> checkValidity(e));
    }

    @Test
    void whenSudokuPartiallySolved_thenFindSolution() {
        SudokuSolver sudokuSolver = new SudokuSolver();
        Integer[][] solvedSudokuBoard = sudokuSolver.findSolution(initialValues);

        checkValidity(solvedSudokuBoard);
        sudokuSolver.printSolution(solvedSudokuBoard);
    }

    private static void checkValidity(Integer[][] e) {
        for (int i = 0; i < 9; i++) {
            //check if all elements in the row are unique
            assertEquals(9, Arrays.stream(e[i]).distinct().count());
            //check all elements are in between 1 and 9
            assertTrue(Arrays.stream(e[i]).allMatch(x -> x >= 1 && x <= 9));
            //check if all elements in the column are unique
            assertEquals(9, Arrays.stream(ArrayUtils.getColumn(e, i)).distinct().count());
            //check all elements are in between 1 and 9
            assertTrue(Arrays.stream(ArrayUtils.getColumn(e, i)).allMatch(x -> x >= 1 && x <= 9));

            //check if all elements in the 3x3 sub-grids are unique
            int rowStart = (i / 3) * 3;
            int colStart = (i % 3) * 3;
            int[] subGrid = new int[9];
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    subGrid[j * 3 + k] = e[rowStart + j][colStart + k];
                }
            }
            assertEquals(9, Arrays.stream(subGrid).distinct().count());
        }
    }

}
