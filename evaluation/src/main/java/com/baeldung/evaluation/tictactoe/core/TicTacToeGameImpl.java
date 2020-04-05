package com.baeldung.evaluation.tictactoe.core;

import com.baeldung.evaluation.tictactoe.port.ILog;
import com.baeldung.evaluation.tictactoe.port.Player;
import com.baeldung.evaluation.tictactoe.port.TicTacToeGame;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicTacToeGameImpl extends TicTacToeGame {
    Player playerX;
    Player playerO;
    Character[][] grid = new Character[3][3];
    ILog logger;
    String moveSequence = "";

    public TicTacToeGameImpl() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    private void registerPlayer(Player player) {
        if (playerX == null) {
            playerX = player;
            this.addObserver(player);
        } else if (playerO == null) {
            playerO = player;
            this.addObserver(player);
        }
    }

    private GameResult checkGameState() {
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
            if (Arrays.stream(check).allMatch(character -> character.equals('X'))) {
                return GameResult.WIN_X;
            }
            if (Arrays.stream(check).allMatch(character -> character.equals('O'))) {
                return GameResult.WIN_O;
            }
        }

        for (Character[] row : grid) {
            if (Arrays.stream(row).anyMatch(character -> character.equals(' '))) {
                return GameResult.PLAYING;
            }
        }

        return GameResult.DRAW;
    }

    @Override public void join(Player player) {
        registerPlayer(player);
    }

    @Override public void move(Player player, int row, int column) {
        grid[row][column] = player.equals(playerX) ? 'X' : 'O';
        moveSequence += "(" + row + "," + column + ")";
        setChanged();
        GameResult result = checkGameState();
        notifyObservers(new ImmutableTriple<>(player, grid, result));
        if (result != GameResult.PLAYING && logger != null)
            logger.saveGame(moveSequence);
    }

    @Override public void setLogger(ILog logger) {
        this.logger = logger;
    }
}
