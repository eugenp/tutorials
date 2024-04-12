package com.baeldung.factory_pattern.abstract_factory;

public class NextGenMotorcycle implements MotorVehicle {
    @Override
    public void build() {
        System.out.println("NextGen Motorcycle");
    }
}