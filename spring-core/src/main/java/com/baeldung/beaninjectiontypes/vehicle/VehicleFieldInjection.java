package com.baeldung.beaninjectiontypes.vehicle;

import com.baeldung.beaninjectiontypes.vehicle.engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleFieldInjection implements Vehicle {

    @Autowired
    private Engine engine;

    @Override
    public Engine getEngine() {
        return engine;
    }
}
