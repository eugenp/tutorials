package com.baeldung.hexagonalarchitecture2.core.domain;

public enum Move {
    ROCK, PAPER, SCISSORS;

    public static Move convertToMove(int i) {
        switch (i) {
        case 0:
            return ROCK;
        case 1:
            return PAPER;
        default:
            return SCISSORS;
        }
    }
}
