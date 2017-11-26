package com.baeldung.beaninjectiontypes.vehicle;

import com.baeldung.beaninjectiontypes.vehicle.engine.Engine;
import com.baeldung.beaninjectiontypes.vehicle.engine.EngineMock;
import org.junit.Assert;
import org.junit.Test;

public class VehicleSetterInjectionUnitTest {

    @Test
    public void whenUsingSetterInjection_thenEngineMockIsInjected() {
        Engine engine = new EngineMock();
        VehicleSetterInjection vehicle = new VehicleSetterInjection();
        vehicle.setEngine(engine);

        Assert.assertTrue(vehicle.getEngine().isMock());
    }
}
