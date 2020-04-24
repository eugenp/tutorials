package com.baeldung.hexagonal.tictactoe.game;

import com.baeldung.hexagonal.tictactoe.consoleadapters.ConsoleDisplayAdapter;
import com.baeldung.hexagonal.tictactoe.consoleadapters.ConsoleInputAdapter;
import com.baeldung.hexagonal.tictactoe.domain.TicTacToe;

public class Game {
    public static void main(String[] args) {
        ConsoleDisplayAdapter display = new ConsoleDisplayAdapter();
        TicTacToe tictactoe = new TicTacToe(display);
        ConsoleInputAdapter input = new ConsoleInputAdapter(tictactoe);
        tictactoe.play(input);
    }
}
