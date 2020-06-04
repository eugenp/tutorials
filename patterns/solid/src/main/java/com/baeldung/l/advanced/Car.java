package com.baeldung.l.advanced;

public abstract class Car {
    // invariant: speed < limit;
    protected int speed, limit;

    protected abstract void turnOnEngine();

    // postcondition: speed < limit
    protected abstract void accelerate();

    // postcondition: speed must reduce
    protected abstract void brake();
}
