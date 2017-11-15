package com.baeldung.beaninjectiontypes.model;

import com.baeldung.beaninjectiontypes.model.engine.Engine;

public class Vehicle {
    private Engine engine;

    public Vehicle() {
    }

    public Vehicle(Engine engine) {
        this.engine =  engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}