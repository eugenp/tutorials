
package com.baeldung.maven.dependency.classifier.provider.factory;

import com.baeldung.maven.dependency.classifier.provider.model.Car;
import com.baeldung.maven.dependency.classifier.provider.model.Car.Type;
import com.baeldung.maven.dependency.classifier.provider.model.PowerSource;
import com.baeldung.maven.dependency.classifier.provider.stub.CarStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CarFactoryUnitTest {

    @Test
    @DisplayName("Given Car type When CarFactory manufacture is called Then create a Car of the given type")
    public void givenCarType_whenCarFactoryManufactureCalled_thenCreateCarOfGivenType() {
        Car car = CarFactory.manufacture(Type.ELECTRIC);

        assertNotNull(car, "CarFactory didn't manufacture a car. Car is null");
        assertEquals(Type.ELECTRIC, car.getType());
    }

    @Test
    @DisplayName("Given an electric car When asked for fuel type Then return Battery")
    public void givenElectricCar_whenAskedForFuelType_thenReturnBattery() {
        Car car = CarStub.ELECTRIC_CAR;

        assertEquals(PowerSource.BATTERY, car.getPowerSource());
    }
}
