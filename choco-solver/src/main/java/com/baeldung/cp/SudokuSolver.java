
package com.baeldung.cp;

import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SudokuSolver {
    private final Logger logger = LoggerFactory.getLogger(SudokuSolver.class);

    private Model sudokuModel;

    public Integer[][] findSolution(int[][] initialValues) {
        IntVar[][] sudokuCells = createModelAndVariables();
        setConstraints(sudokuCells, initialValues);

        Solution solution = sudokuModel.getSolver().findSolution();
        IntVar[] solvedSudokuCells = solution.retrieveIntVars(true).toArray(new IntVar[0]);
        return getNineByNineMatrix(solvedSudokuCells);
    }

    public List<Integer[][]> findSolutions(final int MAX_SOLUTIONS) {
        IntVar[][] sudokuCells = createModelAndVariables();
        setConstraints(sudokuCells);

        List<Integer[][]> solvedSudokuBoards = getSolutions(MAX_SOLUTIONS);
        return solvedSudokuBoards;
    }

    private List<Integer[][]> getSolutions(int MAX_SOLUTIONS) {
        List<Integer[][]> solvedSudokuBoards = new ArrayList<>();
        int solutionCount = 0;
        while (solutionCount++ < MAX_SOLUTIONS && sudokuModel.getSolver().solve()) {
            logger.info("Solution #{}", solutionCount);
            IntVar[] solvedSudokuCells = sudokuModel.retrieveIntVars(true);

            solvedSudokuBoards.add(getNineByNineMatrix(solvedSudokuCells));
            sudokuModel.getSolver().reset();
        }
        return solvedSudokuBoards;
    }

    private IntVar[][] createModelAndVariables() {
        sudokuModel = new Model("Sudoku Solver");
        IntVar[][] sudokuCells = sudokuModel.intVarMatrix("board", 9, 9, 1, 9);
        return sudokuCells;
    }

    private void setConstraints(IntVar[][] sudokuCells) {
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

    private void setConstraints(IntVar[][] sudokuCells, int[][] preSolvedSudokuMatrix) {
        setConstraints(sudokuCells);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (preSolvedSudokuMatrix[i][j] != 0) {
                    sudokuCells[i][j].eq(preSolvedSudokuMatrix[i][j])
                        .post();
                }
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


    private Integer[][] getNineByNineMatrix(IntVar[] sudokuCells) {
        Integer[][] sudokuCellsMatrix = new Integer[9][9];
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuCellsMatrix[i][j] = sudokuCells[index++].getValue();
            }
        }
        return sudokuCellsMatrix;
    }

    public void printSolution(Integer[][] solvedSudokuBoards) {
        StringBuilder stringBuilder = new StringBuilder("\n");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                stringBuilder.append(solvedSudokuBoards[i][j]).append(" ");
                if ((j + 1) % 3 == 0 && (j + 1) % 9 != 0) {
                    stringBuilder.append("| ");
                }
            }
            stringBuilder.append("\n");
            if ((i + 1) % 3 == 0 && (i + 1) % 9 != 0) {
                stringBuilder.append("---------------------\n");
            }
        }
        stringBuilder.append("\n");
        logger.info(stringBuilder.toString());
    }
}