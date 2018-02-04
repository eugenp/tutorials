package com.baeldung.algorithms.sudoku;

import java.util.stream.IntStream;

public class BacktrackingAlgorithm {

    private static final int BOARD_SIZE = 9;
    private static final int SUBSECTION_SIZE = 3;
    private static final int BOARD_START_INDEX = 0;

    private static final int NO_VALUE = 0;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;

    private static int[][] board = {
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

    public static void main(String[] args) {
        BacktrackingAlgorithm solver = new BacktrackingAlgorithm();
        solver.solve(board);
        solver.printBoard();
    }

    private void printBoard() {
        for (int row = BOARD_START_INDEX; row < BOARD_SIZE; row++) {
            for (int column = BOARD_START_INDEX; column < BOARD_SIZE; column++) {
                System.out.print(board[row][column] + " ");
            }
            System.out.println();
        }
    }

    private boolean solve(int[][] board) {
        for (int r = BOARD_START_INDEX; r < BOARD_SIZE; r++) {
            for (int c = BOARD_START_INDEX; c < BOARD_SIZE; c++) {
                if (board[r][c] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[r][c] = k;
                        if (isValid(board, r, c) && solve(board)) {
                            return true;
                        }
                        board[r][c] = NO_VALUE;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int r, int c) {
        return rowConstraint(board, r) &&
          columnConstraint(board, c) &&
          subsectionConstraint(board, r, c);
    }

    private boolean subsectionConstraint(int[][] board, int r, int c) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        int subsectionRowStart = (r / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionRowEnd = subsectionRowStart + SUBSECTION_SIZE;

        int subsectionColumnStart = (c / SUBSECTION_SIZE) * SUBSECTION_SIZE;
        int subsectionColumnEnd = subsectionColumnStart + SUBSECTION_SIZE;

        for (int i = subsectionRowStart; i < subsectionRowEnd; i++) {
            for (int j = subsectionColumnStart; j < subsectionColumnEnd; j++) {
                if (!checkConstraint(board, i, constraint, j)) return false;
            }
        }
        return true;
    }

    private boolean columnConstraint(int[][] board, int c) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
          .allMatch(i -> checkConstraint(board, i, constraint, c));
    }

    private boolean rowConstraint(int[][] board, int r) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        return IntStream.range(BOARD_START_INDEX, BOARD_SIZE)
          .allMatch(i -> checkConstraint(board, r, constraint, i));
    }

    private boolean checkConstraint(int[][] board, int r, boolean[] constraint, int c) {
        if (board[r][c] != NO_VALUE) {
            if (!constraint[board[r][c] - 1]) {
                constraint[board[r][c] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
}
