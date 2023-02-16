package com.baeldung.methods;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

class VehicleProcessorUnitTest {

    VehicleProcessor vehicleProcessor = mock(VehicleProcessor.class);

    @Test
    void givenParameterObject_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Vehicle vehicle = mock(Vehicle.class);

        vehicleProcessor.processVehicle("Vechicle", "Car", "red", 2200, true);
        verify(vehicleProcessor, atLeastOnce()).processVehicle("Vechicle", "Car", "red", 2200, true);

        vehicleProcessor.processVehicle(vehicle);
        verify(vehicleProcessor, atLeastOnce()).processVehicle(vehicle);
    }

    @Test
    void givenJavaBeanPattern_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Motorcycle motorcycle = new Motorcycle("Moto", "Fast", "yellow", 235, true, 2023);
        motorcycle.setFeatures("GPS");

        vehicleProcessor.processVehicle(motorcycle);
        verify(vehicleProcessor, atLeastOnce()).processVehicle(motorcycle);
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("GPS");
    }

    @Test
    void givenJavaVarargs_whenMethodCall_thenAssertTheReturnedConcatenatedString() {
        Motorcycle motorcycle = new Motorcycle("Moto", "Speed", "green", 350, true, 2023);
        motorcycle.addMotorcycleFeatures("abs");
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("abs");

        motorcycle.addMotorcycleFeatures("navi", "charger");
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("abs, navi, charger");

        motorcycle.addMotorcycleFeatures("wifi", "phone", "satelite");
        assertThat(motorcycle.getFeatures()).isEqualToIgnoringCase("abs, navi, charger, wifi, phone, satelite");
    }

    @Test
    void givenJavaBuilderPattern_whenMethodCall_thenVerifyCallIsDoneCorrectly() {
        Car car = new Car.Builder("Car", "Coupe", 2023).color("blue")
            .automatic(true)
            .build();

        vehicleProcessor.processCar(car);
        verify(vehicleProcessor, atLeastOnce()).processCar(car);

    }

}
