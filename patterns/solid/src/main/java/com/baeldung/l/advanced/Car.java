package com.baeldung.l.advanced;

public abstract class Car {
    protected int limit;

    // invariant: speed < limit;
    protected int speed;

    // Allowed to be set once at the time of creation.
    // Value can only increment thereafter.
    // Value cannot be reset.
    protected int mileage;

    public Car(int mileage) {
        this.mileage = mileage;
    }

    protected abstract void turnOnEngine();

    // postcondition: speed < limit
    protected abstract void accelerate();

    // postcondition: speed must reduce
    protected abstract void brake();

}
