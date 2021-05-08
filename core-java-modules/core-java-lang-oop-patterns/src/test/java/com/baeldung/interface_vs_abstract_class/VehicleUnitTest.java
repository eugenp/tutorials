package com.baeldung.interface_vs_abstract_class;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VehicleUnitTest {

    @Test
    void givenVehicle_whenNeedToDrive_thenStart() {
        Vehicle car = new Car("BMW");

        car.start();
        car.drive();
        car.changeGear();
        car.stop();
    }

}
