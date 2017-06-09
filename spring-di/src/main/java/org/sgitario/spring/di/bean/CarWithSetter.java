package org.sgitario.spring.di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarWithSetter {

    @Autowired
    private Engine engine;

    public void run() {
        this.engine.start();
    }
}
