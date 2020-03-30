package com.baeldung.evaluation.tictactoe.adapter.player;

import com.baeldung.evaluation.tictactoe.port.IPlayer;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Random;

public class RandomPlayer implements IPlayer {

    private Character[][] grid;

    @Override public String getName() {
        return "Computer (RANDOM)";
    }

    @Override public ImmutablePair<Integer, Integer> move() {
        Random random = new Random();
        if (grid == null) {
            return new ImmutablePair<>(random.nextInt(3), random.nextInt(3));
        } else {
            while (true) {
                int x = random.nextInt(3);
                int y = random.nextInt(3);
                if (grid[x][y] == ' ') {
                    return new ImmutablePair<>(x, y);
                }
            }
        }
    }

    @Override public void endGame(Result result) {
    }

    @Override public void update(Character[][] grid) {
        this.grid = grid;
    }
}
