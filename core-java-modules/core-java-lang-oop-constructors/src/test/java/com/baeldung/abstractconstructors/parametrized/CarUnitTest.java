package com.baeldung.abstractconstructors.parametrized;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarUnitTest {
    @Test
    void givenParametrizedConstructor_whenConcreteCreation_thenCall() {
        ElectricCar electricCar = new ElectricCar(8);
        assertNotNull(electricCar);
        electricCar.display();

        System.out.println();

        FuelCar fuelCar = new FuelCar("Gasoline");
        assertNotNull(fuelCar);
        fuelCar.display();
    }
}
