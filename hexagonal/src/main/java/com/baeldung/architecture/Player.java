package com.baeldung.architecture;

public class Player {
    private PlayerType type;
    private String name;
    private String club;

    Player(PlayerType type, String name, String club) {
        this.type = type;
        this.name = name;
        this.club = club;
    }

    public String getName() {
        return name;
    }
}
