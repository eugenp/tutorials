package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Coach {

    private Engine engine;

    @Autowired
    public Coach(Engine engine) {
        this.engine = engine;
    }

    public int getHorsePower() {
        return engine.getHorsePower();
    }
}
