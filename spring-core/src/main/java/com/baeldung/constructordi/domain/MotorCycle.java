package com.baeldung.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MotorCycle {
    private Engine engine;

    private Gear gear;

    @Autowired
    public MotorCycle(Engine engine, Gear gear) {
        this.engine = engine;
        this.gear = gear;
    }

    @Override
    public String toString() {
        return "MotorCycle{" + "engine=" + engine + ", numberOfGears=" + gear + '}';
    }
}
