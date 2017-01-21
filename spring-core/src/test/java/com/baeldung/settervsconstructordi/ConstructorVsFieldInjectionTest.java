package com.baeldung.settervsconstructordi;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

public class ConstructorVsFieldInjectionTest {

    @Test
    public void givenCarServiceIsInit_WhenStart_ThenStartedIsReturned() {
        EngineService engineService = new EngineService();
        CarService carService = new CarService(engineService);

        Assert.assertEquals("start must return started", "started", carService.start());
    }

    @Test
    public void givenBusServiceIsInit_WhenStart_ThenStartedIsReturned() {
        EngineService engineService = new EngineService();
        BusService busService = new BusService();
        Whitebox.setInternalState(busService, "engineService", engineService);

        Assert.assertEquals("start must return started", "started", busService.start());
    }
}