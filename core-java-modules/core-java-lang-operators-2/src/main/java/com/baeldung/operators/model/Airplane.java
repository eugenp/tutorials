package com.baeldung.operators.model;

public class Airplane extends Transport {

    @Override
    public String getType() {
        return "air";
    }

    @Override
    public String getName() {
        return "arplane";
    }

    public boolean isPrivate() {
        return false;
    }

}
