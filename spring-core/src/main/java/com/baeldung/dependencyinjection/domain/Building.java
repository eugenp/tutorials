package com.baeldung.dependencyinjection.domain;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

public class Building {
    Escalator escalator;
    AirConditioner airConditioner;
    @Resource(name="waterPump")
    WaterPump waterPump;

    public Building(Escalator escalator) {

        this.escalator = escalator;
    }

    @Autowired
    public void setAirConditioner(AirConditioner airConditioner) {

        this.airConditioner = airConditioner;
    }

    @Override
    public String toString() {

        return escalator.toString() + " and " + airConditioner.toString() + " and " + waterPump.toString();
    }
}
