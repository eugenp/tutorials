package com.baeldung.beaninjectiontypes.vehicle;

import com.baeldung.beaninjectiontypes.vehicle.engine.Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleSetterInjection implements Vehicle {

    private Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public Engine getEngine() {
        return engine;
    }
}
