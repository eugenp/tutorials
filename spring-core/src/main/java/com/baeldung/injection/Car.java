package com.baeldung.injection;

public class Car {
    private Engine engine;
    private SpeedLimiter speedLimiter;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void setSpeedLimiter(SpeedLimiter speedLimiter) {
        this.speedLimiter = speedLimiter;
    }
}
