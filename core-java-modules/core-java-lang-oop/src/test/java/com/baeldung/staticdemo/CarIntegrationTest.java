package com.baeldung.staticdemo;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarIntegrationTest {
    @Test
    public void whenNumberOfCarObjectsInitialized_thenStaticCounterIncreases() {
        new Car("Jaguar", "V8");
        new Car("Bugatti", "W16");
        assertEquals(2, Car.numberOfCars);
    }
}
