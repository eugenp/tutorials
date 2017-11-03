package com.baeldung.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MotorCycle {
    private Engine engine;
    private Gear numberOfGears;

    @Autowired
    public void setNumberOfGears(Gear numberOfGears) {
        this.numberOfGears = numberOfGears;
    }

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "MotorCycle{" + "engine=" + engine + ", numberOfGears=" + numberOfGears + '}';
    }
}