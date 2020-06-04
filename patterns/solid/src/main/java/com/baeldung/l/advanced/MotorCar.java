package com.baeldung.l.advanced;

public class MotorCar extends Car {
    @Override
    protected void turnOnEngine() {
        // Start MotorCar
    }

    @Override
    // postcondition: speed < limit
    protected void accelerate() {
        // Accelerate MotorCar
    }

    @Override
    // postcondition: speed must reduce
    protected void brake() {
        // Apply MotorCar brake
    }
}
