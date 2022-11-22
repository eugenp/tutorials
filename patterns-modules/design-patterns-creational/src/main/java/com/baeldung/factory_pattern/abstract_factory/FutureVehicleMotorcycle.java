package com.baeldung.factory_pattern.abstract_factory;

public class FutureVehicleMotorcycle implements MotorVehicle {
    @Override
    public void build() {
        System.out.println("Future Vehicle Motorcycle");
    }
}