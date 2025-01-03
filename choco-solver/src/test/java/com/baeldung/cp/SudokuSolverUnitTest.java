package com.baeldung.cp;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuSolverUnitTest {

    private final Logger logger = LoggerFactory.getLogger(SudokuSolverUnitTest.class);

    @Test
    void givenASudokuBoard_whenAllCellsBlank_thenFindThreeSolutions() {
        Model sudokuModel = new Model("Sudoku Solver");
        IntVar[][] sudokuCells = sudokuModel.intVarMatrix("board", 9, 9, 1, 9);

        setConstraints(sudokuCells, sudokuModel);

        findSolutions(sudokuModel);
    }

    @Test
    void givenSudokuBoard_whenPartiallySolved_thenFindSolution() {
        Model sudokuModel = new Model("Sudoku Solver");
        IntVar[][] sudokuCells = sudokuModel.intVarMatrix("board", 9, 9, 1, 9);

        setConstraints(sudokuCells, sudokuModel);
        partiallySolve(sudokuCells);

        findSolution(sudokuModel);
    }

    private void findSolution(Model sudokuModel) {
        Solution solution = sudokuModel.getSolver().findSolution();
        IntVar[] sudokuCells = solution.retrieveIntVars(true).toArray(new IntVar[0]);
        printSolution(sudokuCells);
    }

    private void setConstraints(IntVar[][] sudokuCells, Model sudokuModel) {
        for (int i = 0; i < 9; i++) {
            IntVar[] rowCells = getRowCells(sudokuCells, i);
            sudokuModel.allDifferent(rowCells).post();

            IntVar[] columnCells = getColumnCells(sudokuCells, i);
            sudokuModel.allDifferent(columnCells).post();
        }
        // 3x3 sub-grids
        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                IntVar[] cellsInRange = getCellsInRange(j, j + 2, i, i + 2, sudokuCells);
                sudokuModel.allDifferent(cellsInRange).post();
            }
        }
    }

    private IntVar[] getRowCells(IntVar[][] sudokuCells, int rowNum) {
        return sudokuCells[rowNum];
    }

    private IntVar[] getColumnCells(IntVar[][] sudokuCells, int columnNum) {
        IntVar[] columnCells = new IntVar[9];
        for (int i = 0; i < 9; i++) {
            columnCells[i] = sudokuCells[i][columnNum];
        }
        return columnCells;
    }

    private IntVar[] getCellsInRange(int columnLb, int columnUb, int rowLb, int rowUb, IntVar[][] sudokuCells) {
        int size = (columnUb - columnLb + 1) * (rowUb - rowLb + 1);
        IntVar[] cellsInRange = new IntVar[size];
        int index = 0;
        for (int i = rowLb; i <= rowUb; i++) {
            for (int j = columnLb; j <= columnUb; j++) {
                cellsInRange[index++] = sudokuCells[i][j];
            }
        }
        return cellsInRange;
    }

    private void findSolutions(Model model) {
        int solutionCount = 0;
        while (model.getSolver().solve()) {
            if(solutionCount++ > 2) {
                break;
            }
            logger.info("Solution #{}", solutionCount);
            IntVar[] sudokuCells = model.retrieveIntVars(true);
            printSolution(sudokuCells);
            model.getSolver().reset();
        }
    }

    private void printSolution(IntVar[] sudokuCells) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < sudokuCells.length; i++) {
            stringBuilder.append(sudokuCells[i].getValue()).append(" ");
            if ((i + 1) % 9 == 0) {
                stringBuilder.append("\n");
            }
            // after every 3 cells, print a pipe
            if ((i + 1) % 3 == 0 && (i + 1) % 9 != 0) {
                stringBuilder.append("| ");
            }
            // after every 3 rows, print a line
            if ((i + 1) % 27 == 0 && (i + 1) % 81 != 0) {
                stringBuilder.append("---------------------\n");
            }
        }
        stringBuilder.append("\n");
        logger.info(stringBuilder.toString());
    }

    private void partiallySolve(IntVar[][] sudokuCells) {
        int[][] initialValues = partiallyFillSudokuBoard();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (initialValues[i][j] != 0) {
                    sudokuCells[i][j].eq(initialValues[i][j]).post();
                }
            }
        }
    }

    private static int[][] partiallyFillSudokuBoard() {
        int[][] initialValues = {
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
        return initialValues;
    }
}
