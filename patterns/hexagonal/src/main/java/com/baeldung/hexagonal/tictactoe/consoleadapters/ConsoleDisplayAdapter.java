package com.baeldung.hexagonal.tictactoe.consoleadapters;

import com.baeldung.hexagonal.tictactoe.domain.DisplayPort;

public class ConsoleDisplayAdapter implements DisplayPort {

    public char charOf(int i, int r, int c) {
        if (i == 1)
            return 'X';
        else if (i == 2)
            return 'O';
        else {

            return (char) ('0' + (r * 3 + c) + 1);
        }
    }

    public void displayRow(int[] row, int r) {
        System.out.println(charOf(row[0], r, 0) + " | " + charOf(row[1], r, 1) + " | " + charOf(row[2], r, 2));
    }

    @Override
    public void showGrid(int[][] table) {
        displayRow(table[0], 0);
        System.out.println("---------");
        displayRow(table[1], 1);
        System.out.println("---------");
        displayRow(table[2], 2);
    }

    @Override
    public void showInvalidEntry() {
        System.out.println("The entry was invalid");
    }

    @Override
    public void showResult(int status) {
        if (status == 1) {
            System.out.println("Player 1 won!");
        } else if (status == 2) {
            System.out.println("Player 2 won!");
        } else if (status == -1) {
            System.out.println("Tie");
        }

    }

}