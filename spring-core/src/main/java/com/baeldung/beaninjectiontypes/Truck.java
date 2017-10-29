package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Truck {

    @Autowired
    private Engine engine;

    public int getHorsePower() {
        return engine.getHorsePower();
    }
}
