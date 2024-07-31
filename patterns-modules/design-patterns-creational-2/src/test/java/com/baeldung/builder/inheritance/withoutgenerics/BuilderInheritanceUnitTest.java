package com.baeldung.builder.inheritance.withoutgenerics;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BuilderInheritanceUnitTest {

    @Test
    void givenNonGenericImpl_whenBuild_thenReturnObject() {
        Car car = new Car.CarBuilder().colour("red")
            .fuelType("Petrol")
            .make("Ford")
            .model("F")
            .build();
        assertEquals("red", car.getColour());
        assertEquals("Ford", car.getMake());
    }

}
