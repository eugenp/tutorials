package com.baeldung.factorypattern.abstract_factory;

public abstract class Corporation {

    public abstract MotorVehicle createMotorVehicle();

    public abstract ElectricVehicle createElectricVehicle();
}