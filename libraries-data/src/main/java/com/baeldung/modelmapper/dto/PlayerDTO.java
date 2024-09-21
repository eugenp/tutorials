package com.baeldung.modelmapper.dto;

public class PlayerDTO {

    private Long id;
    private String name;

    private GameDTO currentGame;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameDTO getCurrentGame() {
        return this.currentGame;
    }

    public void setCurrentGame(GameDTO currentGame) {
        this.currentGame = currentGame;
    }
}
