package com.baeldung.hexagonal.tictactoe.consoleadapters;

import java.util.Scanner;
import com.baeldung.hexagonal.tictactoe.domain.InputPort;
import com.baeldung.hexagonal.tictactoe.domain.TicTacToeGame;

public class ConsoleInputAdapter implements InputPort {
    Scanner inputScanner;
    TicTacToeGame tictactoe;

    public ConsoleInputAdapter(TicTacToeGame tictactoe) {
        inputScanner = new Scanner(System.in);
        this.tictactoe = tictactoe;
    }

    void start() {
        tictactoe.play(this);
    }
    
    @Override
    public int getInput(boolean playerOne) {
        System.out.print("Player " + (playerOne ? "1: " : "2 :"));
        System.out.println("Choose square");
        return inputScanner.nextInt();
    }

}