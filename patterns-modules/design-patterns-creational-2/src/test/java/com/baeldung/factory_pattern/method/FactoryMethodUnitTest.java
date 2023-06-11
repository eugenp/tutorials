package com.baeldung.factory_pattern.method;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class FactoryMethodUnitTest {
    @Test
    public void givenCarFactory_whenCreateMotorVehicle_thenInstanceOf() {
        MotorVehicleFactory factory = new CarFactory();
        MotorVehicle car = factory.createMotorVehicle();
        assertThat(car).isInstanceOf(MotorVehicle.class);
        assertThat(car).isInstanceOf(Car.class);
    }

    @Test
    public void givenMotorcycleFactory_whenCreateMotorVehicle_thenInstanceOf() {
        MotorVehicleFactory factory = new MotorcycleFactory();
        MotorVehicle motorcycle = factory.createMotorVehicle();
        assertThat(motorcycle).isInstanceOf(MotorVehicle.class);
        assertThat(motorcycle).isInstanceOf(Motorcycle.class);
    }
}