package com.baeldung.algorithms.sudoku;

import java.util.Arrays;

public class DancingLinksAlgorithm {
    private static final int BOARD_SIZE = 9;
    private static final int SUBSECTION_SIZE = 3;
    private static final int NO_VALUE = 0;
    private static final int CONSTRAINTS = 4;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;
    private static final int COVER_START_INDEX = 1;

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
        DancingLinksAlgorithm solver = new DancingLinksAlgorithm();
        solver.solve(board);
    }

    private void solve(int[][] board) {
        boolean[][] cover = initializeExactCoverBoard(board);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver();
    }

    private int getIndex(int row, int col, int num) {
        return (row - 1) * BOARD_SIZE * BOARD_SIZE + (col - 1) * BOARD_SIZE + (num - 1);
    }

    private boolean[][] createExactCoverBoard() {
        boolean[][] coverBoard = new boolean[BOARD_SIZE * BOARD_SIZE * MAX_VALUE][BOARD_SIZE * BOARD_SIZE * CONSTRAINTS];

        int hBase = 0;
        hBase = checkCellConstraint(coverBoard, hBase);
        hBase = checkRowConstraint(coverBoard, hBase);
        hBase = checkColumnConstraint(coverBoard, hBase);
        checkSubsectionConstraint(coverBoard, hBase);
        
        return coverBoard;
    }

    private int checkSubsectionConstraint(boolean[][] coverBoard, int hBase) {
        for (int br = COVER_START_INDEX; br <= BOARD_SIZE; br += SUBSECTION_SIZE) {
            for (int bc = COVER_START_INDEX; bc <= BOARD_SIZE; bc += SUBSECTION_SIZE) {
                for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                    for (int rDelta = 0; rDelta < SUBSECTION_SIZE; rDelta++) {
                        for (int cDelta = 0; cDelta < SUBSECTION_SIZE; cDelta++) {
                            int index = getIndex(br + rDelta, bc + cDelta, n);
                            coverBoard[index][hBase] = true;
                        }
                    }
                }
            }
        }
        return hBase;
    }

    private int checkColumnConstraint(boolean[][] coverBoard, int hBase) {
        for (int c = COVER_START_INDEX; c <= BOARD_SIZE; c++) {
            for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                for (int r1 = COVER_START_INDEX; r1 <= BOARD_SIZE; r1++) {
                    int index = getIndex(r1, c, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkRowConstraint(boolean[][] coverBoard, int hBase) {
        for (int r = COVER_START_INDEX; r <= BOARD_SIZE; r++) {
            for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++, hBase++) {
                for (int c1 = COVER_START_INDEX; c1 <= BOARD_SIZE; c1++) {
                    int index = getIndex(r, c1, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private int checkCellConstraint(boolean[][] coverBoard, int hBase) {
        for (int r = COVER_START_INDEX; r <= BOARD_SIZE; r++) {
            for (int c = COVER_START_INDEX; c <= BOARD_SIZE; c++, hBase++) {
                for (int n = COVER_START_INDEX; n <= BOARD_SIZE; n++) {
                    int index = getIndex(r, c, n);
                    coverBoard[index][hBase] = true;
                }
            }
        }
        return hBase;
    }

    private boolean[][] initializeExactCoverBoard(int[][] board) {
        boolean[][] coverBoard = createExactCoverBoard();
        for (int i = COVER_START_INDEX; i <= BOARD_SIZE; i++) {
            for (int j = COVER_START_INDEX; j <= BOARD_SIZE; j++) {
                int n = board[i - 1][j - 1];
                if (n != NO_VALUE) {
                    for (int num = MIN_VALUE; num <= MAX_VALUE; num++) {
                        if (num != n) {
                            Arrays.fill(coverBoard[getIndex(i, j, num)], false);
                        }
                    }
                }
            }
        }
        return coverBoard;
    }
}