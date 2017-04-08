package com.baeldung.dependencyinjection.domain;

public class Building {
    Escalator escalator;
    AirConditioner airConditioner;

    public Building(Escalator escalator) {

        this.escalator = escalator;
    }

    public void setAirConditioner(AirConditioner airConditioner) {

        this.airConditioner = airConditioner;
    }

    @Override
    public String toString() {

        return escalator.toString() + " and " + airConditioner.toString();
    }
}
