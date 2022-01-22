package com.baeldung.object.type;

public class Deciduous implements Tree {

    private String name;

    public Deciduous(String name) {
        this.name = name;
    }

    @Override
    public boolean isEvergreen() {
        return false;
    }
}
