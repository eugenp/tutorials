package com.baeldung.typesofbeaninjection.domain;

public class Engine {
    private final String type;
    private final int volume;

    public Engine(String type, int volume) {
        this.type = type;
        this.volume = volume;
    }

    public String getType() {
        return type;
    }

    public int getVolume() {
        return volume;
    }

    @Override
    public String toString() {
        return String.format("%s %d", type, volume);
    }
}
