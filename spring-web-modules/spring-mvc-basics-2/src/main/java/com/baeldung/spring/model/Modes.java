package com.baeldung.spring.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Modes {
    ALPHA("A"),
    BETA("B");

    private String text;

    Modes(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @JsonCreator
    public static Modes fromText(String text) {
        for (Modes modes : Modes.values()) {
            if (modes.getText()
                .equals(text)) {
                return modes;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return text;
    }

}
