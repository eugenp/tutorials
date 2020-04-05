package com.baeldung.evaluation.tictactoe;

import com.baeldung.evaluation.tictactoe.adapter.log.FileLogAdapter;
import com.baeldung.evaluation.tictactoe.adapter.player.ConsolePlayer;
import com.baeldung.evaluation.tictactoe.adapter.player.RandomPlayer;
import com.baeldung.evaluation.tictactoe.port.TicTacToeGame;
import com.baeldung.evaluation.tictactoe.core.TicTacToeGameImpl;
import com.baeldung.evaluation.tictactoe.port.ILog;

public class App {
    public static void main(String[] args) {
        ILog logAdapter = new FileLogAdapter("log.txt");
        TicTacToeGame game = new TicTacToeGameImpl();
        game.setLogger(logAdapter);
        ConsolePlayer playerX = new ConsolePlayer(game);
        RandomPlayer playerO = new RandomPlayer(game);

        while (playerX.canMove()) {
            playerX.consoleMove();
            if (playerO.canMove())
                playerO.randomMove();
        }
    }
}
