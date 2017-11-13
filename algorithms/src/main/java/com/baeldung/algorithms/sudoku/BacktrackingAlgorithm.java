package com.baeldung.algorithms.sudoku;

public class BacktrackingAlgorithm {

    public static void main(String[] args) {
        
        char[][] board = { 
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
        
        BacktrackingAlgorithm solver = new BacktrackingAlgorithm();
        solver.solve(board);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public boolean solve(char[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == '.') {
                    for (int k = 1; k <= 9; k++) {
                        board[r][c] = (char) ('0' + k);
                        if (isValid(board, r, c) && solve(board)) {
                            return true;
                        } else {
                            board[r][c] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean isValid(char[][] board, int r, int c) {
        boolean[] row = new boolean[9];
        for (int i = 0; i < 9; i++) {
            if (board[r][i] >= '1' && board[r][i] <= '9') {
                if (row[board[r][i] - '1'] == false) {
                    row[board[r][i] - '1'] = true;
                } else {
                    return false;
                }
            }
        }

        boolean[] col = new boolean[9];
        for (int i = 0; i < 9; i++) {
            if (board[i][c] >= '1' && board[i][c] <= '9') {
                if (col[board[i][c] - '1'] == false) {
                    col[board[i][c] - '1'] = true;
                } else {
                    return false;
                }
            }
        }
        
        boolean[] grid = new boolean[9];
        for (int i = (r / 3) * 3; i < (r / 3) * 3 + 3; i++) {
            for (int j = (c / 3) * 3; j < (c / 3) * 3 + 3; j++) {
                if (board[i][j] >= '1' && board[i][j] <= '9') {
                    if (grid[board[i][j] - '1'] == false) {
                        grid[board[i][j] - '1'] = true;
                    } else {
                        return false;
                    }
                }
             }
        }
        return true;
    }
}
