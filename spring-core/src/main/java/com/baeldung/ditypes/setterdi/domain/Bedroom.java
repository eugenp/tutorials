package com.baeldung.ditypes.setterdi.domain;

public class Bedroom {

    private String name;

    public Bedroom(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bedroom [name=" + name + "]";
    }

}
