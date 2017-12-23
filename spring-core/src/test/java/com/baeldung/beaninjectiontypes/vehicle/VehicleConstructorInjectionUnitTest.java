package com.baeldung.beaninjectiontypes.vehicle;

import com.baeldung.beaninjectiontypes.vehicle.engine.Engine;
import com.baeldung.beaninjectiontypes.vehicle.engine.EngineMock;
import org.junit.Assert;
import org.junit.Test;

public class VehicleConstructorInjectionUnitTest {

    @Test
    public void whenUsingConstructorInjection_thenEngineMockIsInjected() {
        Engine engine = new EngineMock();
        Vehicle vehicle = new VehicleConstructorInjection(engine);

        Assert.assertTrue(vehicle.getEngine().isMock());
    }
}
