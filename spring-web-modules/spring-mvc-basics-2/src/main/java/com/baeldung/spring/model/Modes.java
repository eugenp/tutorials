package com.baeldung.spring.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Modes {
    ALPHA("A"),
    BETA("B");

    private final String text;

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
        throw new IllegalArgumentException("Unknown mode value: " + text);
    }

    @Override
    public String toString() {
        return text;
    }

}
