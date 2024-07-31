package com.baeldung.jackson.booleanAsInt;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class GameAnnotatedByJsonFormat {

    private Long id;
    private String name;

    @JsonFormat(shape = Shape.NUMBER)
    private boolean paused;

    @JsonFormat(shape = Shape.NUMBER)
    private boolean over;

    public GameAnnotatedByJsonFormat() {
    }

    public GameAnnotatedByJsonFormat(Long id, String name) {
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

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
