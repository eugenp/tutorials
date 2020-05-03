package com.baeldung.staticmodifier;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarUnitTest {
    @Test
    public void whenNumberOfCarObjectsInitialized_thenStaticCounterIncreases() {
        new Car("Jaguar", "V8");
        new Car("Bugatti", "W16");
        assertEquals(2, Car.numberOfCars);
    }
}
