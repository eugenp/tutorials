package com.baeldung.beaninjection.domain;

public class Armor {
    private int thickness;

    public Armor(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public String toString() {
        return "Armor{" +
                "thickness=" + thickness +
                '}';
    }
}
