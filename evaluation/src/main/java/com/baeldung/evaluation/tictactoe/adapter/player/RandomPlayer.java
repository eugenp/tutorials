package com.baeldung.evaluation.tictactoe.adapter.player;

import com.baeldung.evaluation.tictactoe.port.TicTacToeGame;
import com.baeldung.evaluation.tictactoe.port.Player;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import com.baeldung.evaluation.tictactoe.port.TicTacToeGame.GameResult;

import java.util.Observable;
import java.util.Random;

public class RandomPlayer extends Player {
    private Character[][] grid;
    private boolean gameOver;

    public RandomPlayer(TicTacToeGame game) {
        super(game);
    }

    public void randomMove() {
        Random random = new Random();
        if (grid == null) {
            move(random.nextInt(3), random.nextInt(3));
        } else {
            while (true) {
                int x = random.nextInt(3);
                int y = random.nextInt(3);
                if (grid[x][y] == ' ') {
                    move(x, y);
                    return;
                }
            }
        }
    }

    @Override public void update(Observable o, Object update) {
        ImmutableTriple gameState = (ImmutableTriple) update;
        grid = (Character[][]) gameState.middle;
        gameOver = gameState.right != GameResult.PLAYING;
    }

    @Override public boolean canMove() {
        return !gameOver;
    }
}
