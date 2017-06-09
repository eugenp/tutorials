package org.sgitario.spring.di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarWithConstructor {

    private final Engine engine;

    @Autowired
    public CarWithConstructor(Engine engine) {
        this.engine = engine;
    }

    public void run() {
        this.engine.start();
    }
}
