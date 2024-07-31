package com.baeldung.factory_pattern.abstract_factory;

public abstract class Corporation {

    public abstract MotorVehicle createMotorVehicle();

    public abstract ElectricVehicle createElectricVehicle();
}