package com.baeldung.jackson.booleanAsInt;

public class Game {

    private Long id;
    private String name;
    private Boolean paused;
    private Boolean over;

    public Game() {
    }

    public Game(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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

    public Boolean isPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }

    public Boolean isOver() {
        return over;
    }

    public void setOver(Boolean over) {
        this.over = over;
    }
}
