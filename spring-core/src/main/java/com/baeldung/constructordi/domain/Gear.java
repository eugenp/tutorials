package com.baeldung.constructordi.domain;

public class Gear {
    private String numberOfGears;

    public Gear(String numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    @Override
    public String toString() {
        return String.format("%s", numberOfGears);
    }
}
