package com.baeldung.oop;

public class Vehicle {
    private int wheels;
    private String model;

    public Vehicle(int wheels, String model) {
        this.wheels = wheels;
        this.model = model;
    }

    public void start() {
        // the process of starting the vehicle
    }

    public void stop() {
        // process to stop the vehicle
    }

    public void honk() {
        // produces a default honk
    }
}
