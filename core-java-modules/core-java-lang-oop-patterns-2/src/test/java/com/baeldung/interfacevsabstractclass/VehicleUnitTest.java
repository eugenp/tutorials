package com.baeldung.interfacevsabstractclass;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baeldung.interfacevsabstractclass.Car;
import com.baeldung.interfacevsabstractclass.Vehicle;

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
