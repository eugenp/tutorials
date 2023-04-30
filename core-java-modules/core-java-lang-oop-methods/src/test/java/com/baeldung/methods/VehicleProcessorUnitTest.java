package com.baeldung.methods;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class VehicleProcessorUnitTest {

    VehicleProcessor vehicleProcessor = new VehicleProcessor();
    Vehicle vehicle = new Vehicle("Ford", "Focus", "red", 2200, true);

    @Test
    void givenAllArguments_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Vehicle veh = vehicleProcessor.processVehicle("Ford", "Focus", "red", 2200, true);
        assertThat(veh.toString()).hasToString(vehicle.toString());
    }

    @Test
    void givenParameterObject_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Vehicle veh = vehicleProcessor.processVehicle(vehicle);
        assertThat(veh.toString()).hasToString(vehicle.toString());
    }

    @Test
    void givenJavaBeanPattern_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Motorcycle motorcycle = new Motorcycle("Ducati", "Monster", "yellow", 235, true, 2023);
        motorcycle.setFeatures("GPS");

        vehicleProcessor.processVehicle(motorcycle);
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("GPS");
    }

    @Test
    void givenJavaVarargs_whenMethodCall_thenAssertTheReturnedConcatenatedString() {
        Motorcycle motorcycle = new Motorcycle("Ducati", "Monster", "red", 350, true, 2023);
        motorcycle.addMotorcycleFeatures("abs");
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("abs");

        motorcycle.addMotorcycleFeatures("navi", "charger");
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("abs, navi, charger");

        motorcycle.addMotorcycleFeatures("wifi", "phone", "satellite");
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("abs, navi, charger, wifi, phone, satellite");
    }

    @Test
    void givenJavaBuilderPattern_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Car car = new Car.CarBuilder("Ford", "Focus", 2023).color("blue")
            .automatic(true)
            .features("abs, navi, charger, wifi, phone, satellite")
            .build();

        Car result = vehicleProcessor.processCar(car);

        assertThat(result.toString()).hasToString(car.toString());
    }

}
