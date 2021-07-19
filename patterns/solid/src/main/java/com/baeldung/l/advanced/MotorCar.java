package com.baeldung.l.advanced;

public class MotorCar extends Car {

    public MotorCar(int mileage) {
        super(mileage);
    }

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
