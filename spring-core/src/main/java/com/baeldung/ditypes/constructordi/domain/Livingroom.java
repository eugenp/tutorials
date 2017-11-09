package com.baeldung.ditypes.constructordi.domain;

public class Livingroom {

    private String name;

    public Livingroom(String name) {
        super();
        this.name = name;
    }

    @Override
    public String toString() {
        return "Livingroom [name=" + name + "]";
    }

}
