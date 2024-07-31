package com.baeldung.oop;

public class ArmoredCar extends Car {
    private int bulletProofWindows;

    public ArmoredCar(String type, String model, String color) {
        super(type, model, color);
    }

    public void remoteStartCar() {
        // this vehicle can be started by using a remote control
    }
}
