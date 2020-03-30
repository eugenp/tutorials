package com.baeldung.evaluation.tictactoe;

import com.baeldung.evaluation.tictactoe.adapter.log.FileLog;
import com.baeldung.evaluation.tictactoe.adapter.player.ConsolePlayer;
import com.baeldung.evaluation.tictactoe.adapter.player.RandomPlayer;

public class App {
    public static void main(String[] args) {
        new TicTacToeGame(new ConsolePlayer("Human"), new RandomPlayer(), new FileLog()).start();
    }
}
