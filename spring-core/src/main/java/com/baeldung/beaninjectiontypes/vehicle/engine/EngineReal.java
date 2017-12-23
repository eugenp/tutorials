package com.baeldung.beaninjectiontypes.vehicle.engine;

import org.springframework.stereotype.Component;

@Component
public class EngineReal implements Engine {

    @Override
    public boolean isMock() {
        return false;
    }
}
