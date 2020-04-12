package com.baeldung.hexagonal.tictactoe.game;

import com.baeldung.hexagonal.tictactoe.consoleadapters.ConsoleDisplayAdapter;
import com.baeldung.hexagonal.tictactoe.consoleadapters.ConsoleInputAdapter;
import com.baeldung.hexagonal.tictactoe.domain.TicTacToe;

public class Game {
    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe(new ConsoleInputAdapter(), new ConsoleDisplayAdapter());
        tictactoe.play();
    }
}
