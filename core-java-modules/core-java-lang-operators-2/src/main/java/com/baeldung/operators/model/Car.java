package com.baeldung.operators.model;

public class Car extends Transport {

    @Override
    public String getType() {
        return "terrestrial";
    }

    @Override
    public String getName() {
        return "car";
    }

    public boolean hasAirBag() {
        return false;
    }

}
