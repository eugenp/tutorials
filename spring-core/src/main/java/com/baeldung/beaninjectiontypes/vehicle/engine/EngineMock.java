package com.baeldung.beaninjectiontypes.vehicle.engine;

import org.springframework.stereotype.Component;

@Component
public class EngineMock implements Engine {

    @Override
    public boolean isMock() {
        return true;
    }
}
