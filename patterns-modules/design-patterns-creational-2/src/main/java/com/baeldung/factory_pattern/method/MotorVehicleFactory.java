package com.baeldung.factory_pattern.method;

public abstract class MotorVehicleFactory {
    public MotorVehicle create() {
        MotorVehicle vehicle = createMotorVehicle();
        vehicle.build();
        return vehicle;
    }

    protected abstract MotorVehicle createMotorVehicle();
}