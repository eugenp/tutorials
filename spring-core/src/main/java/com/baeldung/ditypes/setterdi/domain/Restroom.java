package com.baeldung.ditypes.setterdi.domain;

public class Restroom {

    private String name;

    public Restroom(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restroom [name=" + name + "]";
    }

}
