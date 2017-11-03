package com.baeldung.setterdi.domain;

public class Gear {
    private String numberOfGears;

    public Gear() {
    }

    public void setNumberOfGears(String numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    @Override
    public String toString() {
        return String.format("%s", numberOfGears);
    }
}
