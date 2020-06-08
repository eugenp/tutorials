package com.baeldung.l.advanced;

public abstract class Car {
    protected int limit;

    // invariant: speed < limit;
    protected int speed;

    protected abstract void turnOnEngine();

    // postcondition: speed < limit
    protected abstract void accelerate();

    // postcondition: speed must reduce
    protected abstract void brake();
}
