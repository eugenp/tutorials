package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LawnMower {

    @Autowired
    private Engine engine;

    public LawnMower(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }
}
