package com.baeldung.algorithms.sudoku;

import java.util.stream.IntStream;

public class BacktrackingAlgorithm {
    
    private static int BOARD_SIZE = 9;
    private static int SUBSECTION_SIZE = 3;
    private static int BOARD_INDEX_START = 0;
    
    private static int NO_VALUE = 0;
    private static int MIN_VALUE = 1;
    private static int MAX_VALUE = 9;

    public static int[][] board = { 
        { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
        { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
        { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
        { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
        { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
        { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
        { 0, 9, 0, 0, 0, 0, 4, 0, 0 } 
    };

    public static void main(String[] args) {
        BacktrackingAlgorithm solver = new BacktrackingAlgorithm();
        solver.solve(board);
        solver.printBoard();
    }

    public void printBoard() {
        IntStream.range(BOARD_INDEX_START, BOARD_SIZE).forEach(row -> {
            IntStream.range(BOARD_INDEX_START, BOARD_SIZE).forEach(column -> {
                System.out.print(board[row][column] + " ");
            });
            System.out.println();
        });
    }

    public boolean solve(int[][] board) {
        for (int r = BOARD_INDEX_START; r < BOARD_SIZE; r++) {
            for (int c = BOARD_INDEX_START; c < BOARD_SIZE; c++) {
                if (board[r][c] == NO_VALUE) {
                    for (int k = MIN_VALUE; k <= MAX_VALUE; k++) {
                        board[r][c] = k;
                        if (isValid(board, r, c) && solve(board)) {
                            return true;
                        } else {
                            board[r][c] = NO_VALUE;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValid(int[][] board, int r, int c) {
        return (rowConstraint(board, r) && 
                columnConstraint(board, c) &&
                subsectionConstraint(board, r, c));
    }

    private boolean subsectionConstraint(int[][] board, int r, int c) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int i = (r / SUBSECTION_SIZE) * SUBSECTION_SIZE; i < (r / SUBSECTION_SIZE) * SUBSECTION_SIZE + SUBSECTION_SIZE; i++) {
            for (int j = (c / SUBSECTION_SIZE) * SUBSECTION_SIZE; j < (c / SUBSECTION_SIZE) * SUBSECTION_SIZE + SUBSECTION_SIZE; j++) {
                if (!checkConstraint(board, i, constraint, j)) return false;
            }
        }
        return true;
    }

    private boolean columnConstraint(int[][] board, int c) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int i = BOARD_INDEX_START; i < BOARD_SIZE; i++) {
            if (!checkConstraint(board, i, constraint, c)) return false;
        }
        return true;
    }

    private boolean rowConstraint(int[][] board, int r) {
        boolean[] constraint = new boolean[BOARD_SIZE];
        for (int i = BOARD_INDEX_START; i < BOARD_SIZE; i++) {
            if (!checkConstraint(board, r, constraint, i)) return false;
        }
        return true;
    }

    private boolean checkConstraint(int[][] board, int r, boolean[] constraint, int c) {
        if (board[r][c] >= MIN_VALUE && board[r][c] <= MAX_VALUE) {
            if (constraint[board[r][c] - 1] == false) {
                constraint[board[r][c] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
}
