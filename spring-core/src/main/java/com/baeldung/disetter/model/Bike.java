package com.baeldung.disetter.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Bike")
public class Bike {
    private Engine engine;
    private Tyre tyre;
    
    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Autowired
    public void setTyre(Tyre tyre) {
        this.tyre = tyre;
    }

    @Override
    public String toString() {
        return "Bike characteristics:\n" + this.engine.toString() + "\n" + this.tyre.toString();
    }
}