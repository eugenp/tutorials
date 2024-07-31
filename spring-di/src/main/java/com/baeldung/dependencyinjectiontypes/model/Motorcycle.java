package com.baeldung.dependencyinjectiontypes.model;

public class Motorcycle extends Vehicle {
    private boolean twoWheeler;

    public Motorcycle(String name, String manufacturer, boolean twoWheeler) {
        super(name, manufacturer);
        this.twoWheeler = true;
    }

    public boolean isTwoWheeler() {
        return twoWheeler;
    }

    public void setTwoWheeler(boolean twoWheeler) {
        this.twoWheeler = twoWheeler;
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "twoWheeler=" + twoWheeler +
                '}';
    }
}
