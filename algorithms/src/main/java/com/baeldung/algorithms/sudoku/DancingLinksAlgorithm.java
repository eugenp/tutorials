package com.baeldung.algorithms.sudoku;

import java.util.*;

public class DancingLinksAlgorithm {
    private static int S = 9;
    private static int SIDE = 3;

    static char[][] board = { 
            { '8', '.', '.', '.', '.', '.', '.', '.', '.' }, 
            { '.', '.', '3', '6', '.', '.', '.', '.', '.' }, 
            { '.', '7', '.', '.', '9', '.', '2', '.', '.' }, 
            { '.', '5', '.', '.', '.', '7', '.', '.', '.' },
            { '.', '.', '.', '.', '4', '5', '7', '.', '.' }, 
            { '.', '.', '.', '1', '.', '.', '.', '3', '.' }, 
            { '.', '.', '1', '.', '.', '.', '.', '6', '8' }, 
            { '.', '.', '8', '5', '.', '.', '.', '1', '.' }, 
            { '.', '9', '.', '.', '.', '.', '4', '.', '.' } 
        };

    public static void main(String[] args) {

        DancingLinksAlgorithm solver = new DancingLinksAlgorithm();
        solver.solve(board);
    }

    public boolean solve(char[][] board) {
        boolean[][] cover = initializeExactCoverBoard(board);
        DancingLinks dlx = new DancingLinks(cover);
        dlx.runSolver();

        return true;
    }

    private int getIndex(int row, int col, int num) {
        return (row - 1) * S * S + (col - 1) * S + (num - 1);
    }

    private boolean[][] createExactCoverBoard() {
        boolean[][] R = new boolean[9 * 9 * 9][9 * 9 * 4];

        int hBase = 0;

        for (int r = 1; r <= S; r++) {
            for (int c = 1; c <= S; c++, hBase++) {
                for (int n = 1; n <= S; n++) {
                    int index = getIndex(r, c, n);
                    R[index][hBase] = true;
                }
            }
        }

        for (int r = 1; r <= S; r++) {
            for (int n = 1; n <= S; n++, hBase++) {
                for (int c1 = 1; c1 <= S; c1++) {
                    int index = getIndex(r, c1, n);
                    R[index][hBase] = true;
                }
            }
        }

        for (int c = 1; c <= S; c++) {
            for (int n = 1; n <= S; n++, hBase++) {
                for (int r1 = 1; r1 <= S; r1++) {
                    int index = getIndex(r1, c, n);
                    R[index][hBase] = true;
                }
            }
        }

        for (int br = 1; br <= S; br += SIDE) {
            for (int bc = 1; bc <= S; bc += SIDE) {
                for (int n = 1; n <= S; n++, hBase++) {
                    for (int rDelta = 0; rDelta < SIDE; rDelta++) {
                        for (int cDelta = 0; cDelta < SIDE; cDelta++) {
                            int index = getIndex(br + rDelta, bc + cDelta, n);
                            R[index][hBase] = true;
                        }
                    }
                }
            }
        }
        return R;
    }

    private boolean[][] initializeExactCoverBoard(char[][] sudoku) {
        boolean[][] R = createExactCoverBoard();
        for (int i = 1; i <= S; i++) {
            for (int j = 1; j <= S; j++) {
                char n = sudoku[i - 1][j - 1];
                if (n != '.') {
                    for (int num = 1; num <= S; num++) {
                        if ((char) ('0' + num) != n) {
                            Arrays.fill(R[getIndex(i, j, num)], false);
                        }
                    }
                }
            }
        }
        return R;
    }
}