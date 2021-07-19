package com.baeldung.constructordi.domain;

public class Engine {
    private String type;
    private int volume;

    public Engine(String type, int volume) {
        this.type = type;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return String.format("%s %d", type, volume);
    }
}
