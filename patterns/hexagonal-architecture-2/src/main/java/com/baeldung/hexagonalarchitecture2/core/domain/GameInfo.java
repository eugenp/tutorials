package com.baeldung.hexagonalarchitecture2.core.domain;

public class GameInfo {
    private int id;
    private Move move;
    private String key;

    public GameInfo(int id, Move move, String key) {
        this.id = id;
        this.move = move;
        this.key = key;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
