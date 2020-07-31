package com.baeldung.hexagonalarchitecture2.core.domain;

public class Game {

    private int id;
    private String moveHMAC;

    public Game(int id, String moveHMAC) {
        this.id = id;
        this.moveHMAC = moveHMAC;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoveHMAC() {
        return moveHMAC;
    }

    public void setMoveHMAC(String moveHMAC) {
        this.moveHMAC = moveHMAC;
    }
}
