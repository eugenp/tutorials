package com.baeldung.factory_pattern.abstract_factory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AbstractFactoryUnitTest {
    @Test
    public void givenFutureVehicleFactory_whenCreateMotorVehicle_thenInstanceOf() {
        Corporation corporation = new FutureVehicleCorporation();
        MotorVehicle motorVehicle = corporation.createMotorVehicle();
        assertThat(motorVehicle).isInstanceOf(MotorVehicle.class);
        assertThat(motorVehicle).isInstanceOf(FutureVehicleMotorcycle.class);
    }

    @Test
    public void givenFutureVehicleFactory_whenCreateElectricVehicle_thenInstanceOf() {
        Corporation corporation = new FutureVehicleCorporation();
        ElectricVehicle electricVehicle = corporation.createElectricVehicle();
        assertThat(electricVehicle).isInstanceOf(ElectricVehicle.class);
        assertThat(electricVehicle).isInstanceOf(FutureVehicleElectricCar.class);
    }

    @Test
    public void givenNextGenFactory_whenCreateMotorVehicle_thenInstanceOf() {
        Corporation corporation = new NextGenCorporation();
        MotorVehicle motorVehicle = corporation.createMotorVehicle();
        assertThat(motorVehicle).isInstanceOf(MotorVehicle.class);
        assertThat(motorVehicle).isInstanceOf(NextGenMotorcycle.class);
    }

    @Test
    public void givenNextGenFactory_whenCreateElectricVehicle_thenInstanceOf() {
        Corporation corporation = new NextGenCorporation();
        ElectricVehicle electricVehicle = corporation.createElectricVehicle();
        assertThat(electricVehicle).isInstanceOf(ElectricVehicle.class);
        assertThat(electricVehicle).isInstanceOf(NextGenElectricCar.class);
    }
}