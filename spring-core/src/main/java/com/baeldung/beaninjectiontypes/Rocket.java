package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Rocket {

    private Engine engine;

    @Autowired
    public Rocket(Engine engine) {
        this.engine = engine;
    }

    public Engine getEngine() {
        return engine;
    }

}
