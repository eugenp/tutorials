package com.baeldung.l.advanced;

public class HybridCar extends Car {
    // invariant: charge >= 0;
    private int charge;

    public HybridCar(int mileage) {
        super(mileage);
    }

    @Override
    protected void turnOnEngine() {
        // Start HybridCar
    }

    @Override
    // postcondition: speed < limit
    protected void accelerate() {
        // Accelerate HybridCar speed < limit
    }

    @Override
    // postcondition: speed must reduce
    // postcondition: charge must increase
    protected void brake() {
        // Apply HybridCar brake
    }
}
