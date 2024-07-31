package com.baeldung.maven.classifier.consumer;

import com.baeldung.maven.classifier.consumer.FuelStation.Zone;
import com.baeldung.maven.dependency.classifier.provider.model.Car;
import com.baeldung.maven.dependency.classifier.provider.stub.CarStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FuelStationUnitTest {

    @Test
    @DisplayName("Given fuel type battery When request for refill Then Return Battery Zone")
    public void givenFuelTypeBattery_whenRequestToRefill_thenReturnBatteryZone() {
        FuelStation fuelStation = new FuelStation();
        Car electricCar = CarStub.ELECTRIC_CAR;

        assertEquals(Zone.BATTERY, fuelStation.refill(electricCar));
    }
}
