package com.baeldung.object.type;

public class Evergreen implements Tree {

    private String name;

    public Evergreen(String name) {
        this.name = name;
    }


    @Override
    public boolean isEvergreen() {
        return true;
    }
}
