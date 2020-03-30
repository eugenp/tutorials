package com.baeldung.evaluation.tictactoe.port;

import org.apache.commons.lang3.tuple.ImmutablePair;

public interface IPlayer {
    enum Result {WIN, LOSE, DRAW}

    String getName();

    ImmutablePair<Integer, Integer> move();

    void endGame(Result result);

    void update(Character[][] grid);
}
