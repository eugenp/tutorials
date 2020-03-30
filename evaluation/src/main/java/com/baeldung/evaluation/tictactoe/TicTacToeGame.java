package com.baeldung.evaluation.tictactoe;

import com.baeldung.evaluation.tictactoe.port.ILog;
import com.baeldung.evaluation.tictactoe.port.IPlayer;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeGame {

    Character[][] grid = new Character[3][3];
    IPlayer playerX;
    IPlayer playerO;
    ILog log;
    String game = "";

    public TicTacToeGame(IPlayer playerX, IPlayer playerO, ILog log) {
        this(playerX, playerO);
        this.log = log;
    }

    public TicTacToeGame(IPlayer playerX, IPlayer playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public void start() {
        boolean gameOver = false;
        for (int i = 0; i < 9 && !gameOver; i++) {
            IPlayer player = (i % 2 == 0) ? playerX : playerO;
            IPlayer opponent = (i % 2 == 0) ? playerO : playerX;
            Character symbol = (i % 2 == 0) ? 'X' : 'O';
            player.update(grid);
            ImmutablePair<Integer, Integer> move = player.move();
            grid[move.left][move.right] = symbol;
            game += "(" + move.left + move.right + ")";
            if (checkVictory(symbol)) {
                gameOver = true;
                player.endGame(IPlayer.Result.WIN);
                opponent.endGame(IPlayer.Result.LOSE);
            }
        }
        if (!gameOver) {
            playerX.endGame(IPlayer.Result.DRAW);
            playerO.endGame(IPlayer.Result.DRAW);
        }
        if (log != null) {
            log.saveGame(playerX.getName(), playerO.getName(), game);
        }
    }

    private boolean checkVictory(Character symbol) {
        List<Character[]> checks = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Character[] row = grid[i];
            Character[] column = { grid[0][i], grid[1][i], grid[2][i] };
            checks.add(row);
            checks.add(column);
        }
        checks.add(new Character[] { grid[0][0], grid[1][1], grid[2][2] });
        checks.add(new Character[] { grid[0][2], grid[1][1], grid[2][0] });

        for (Character[] check : checks) {
            if (Arrays.stream(check).allMatch(character -> character.equals(symbol))) {
                return true;
            }
        }

        return false;
    }
}
