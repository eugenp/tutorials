package com.baeldung.modelmapper.domain;

public class GameSettings {

    private GameMode mode;
    private int maxPlayers;

    public GameSettings() {
    }

    public GameSettings(GameMode mode, int maxPlayers) {
        this.mode = mode;
        this.maxPlayers = maxPlayers;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
