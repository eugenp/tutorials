package com.baeldung.l.advanced;

public class HybridCar extends Car {
    // invariant: charge >= 0;
    private int charge;

    @Override
    protected void turnOnEngine() {
        // Start HybridCar
    }

    @Override
    // postcondition: speed < limit
    protected void accelerate() {
        // Accelerate HybridCar
    }

    @Override
    // postcondition: speed must reduce
    // postcondition: charge must increase
    protected void brake() {
        // Apply HybridCar brake
    }
}
