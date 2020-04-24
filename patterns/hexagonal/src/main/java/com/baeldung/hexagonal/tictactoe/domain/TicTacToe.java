package com.baeldung.hexagonal.tictactoe.domain;

public class TicTacToe implements TicTacToeGame {
    private DisplayPort display;
    private int[][] table;

    public TicTacToe(DisplayPort display) {
        this.display = display;
        table = new int[3][3];
    }

    @Override
    public void play(InputPort input) {
        boolean playerOne = true;
        int status = 0;

        while (status == 0) {
            display.showGrid(table);
            int square = input.getInput(playerOne);
            while (!isValid(square)) {
                display.showInvalidEntry();
                square = input.getInput(playerOne);
            }
            addTic(square, playerOne);
            status = checkWhoWon();
            playerOne = !playerOne;
        }
        display.showResult(status);
    }

    private int checkWhoWon() {

        // check row
        for (int row = 0; row < 3; row++) {
            if (table[row][0] != 0 && table[row][0] == table[row][1] && table[row][1] == table[row][2]) {
                return table[row][0];
            }
        }

        // check column
        for (int col = 0; col < 3; col++) {
            if (table[0][col] != 0 && table[0][col] == table[1][col] && table[1][col] == table[2][col]) {
                return table[0][col];
            }
        }

        // check diagonals
        if (table[1][1] != 0) {
            if (table[0][0] == table[1][1] && table[0][0] == table[2][2]) {
                return table[1][1];
            }

            if (table[0][2] == table[1][1] && table[0][2] == table[2][0]) {
                return table[1][1];
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (table[row][col] == 0)
                    return 0;
            }
        }
        return -1;
    }

    private boolean isValid(int number) {
        number -= 1;
        return (table[number / 3][number % 3] == 0);
    }

    private void addTic(int number, boolean playerOne) {
        number -= 1;
        table[number / 3][number % 3] = playerOne ? 1 : 2;
    }

}
