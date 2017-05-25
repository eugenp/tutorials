package com.baeldung.diconst.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("Bike")
public class Bike {
    private Engine engine;
    private Tyre tyre;
    
    @Autowired
    public Bike(Engine engine, Tyre tyre) {
        this.engine = engine;
        this.tyre = tyre;
    }
    
    @Override
    public String toString() {
        return "Bike characteristics:\n" + this.engine.toString() + "\n" + this.tyre.toString();
    }
}
