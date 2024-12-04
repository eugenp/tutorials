package com.baeldung.builder.inheritance.withgenerics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BuilderInheritanceUnitTest {

    @Test
    void givenGenericImpl_whenBuild_thenReturnObject() {
        Car.Builder<?> carBuilder1 = new Car.Builder();
        Car car = carBuilder1.colour("red")
            .fuelType("Petrol")
            .make("Ford")
            .model("F")
            .build();

        ElectricCar.Builder<?> ElectricCarBuilder = new ElectricCar.Builder();
        ElectricCar eCar = ElectricCarBuilder.make("Mercedes")
            .colour("White")
            .model("G")
            .fuelType("Electric")
            .batteryType("Lithium")
            .build();

        assertEquals("red", car.getColour());
        assertEquals("Ford", car.getMake());

        assertEquals("Electric", eCar.getFuelType());
        assertEquals("Lithium", eCar.getBatteryType());
    }

}
