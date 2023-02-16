package com.baeldung.methods;

public class Vehicle {

    static String defaultValue = "DEFAULT";
    private String make = defaultValue;
    private String model = defaultValue;
    private String color = defaultValue;
    private int weight = 0;
    private boolean statusNew = true;

    public Vehicle() {
        super();
    }

    public Vehicle(String make, String model, String color, int weight, boolean statusNew) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.weight = weight;
        this.statusNew = statusNew;
    }

    public Vehicle(Vehicle vehicle) {
        this.make = vehicle.make;
        this.model = vehicle.model;
        this.color = vehicle.color;
        this.weight = vehicle.weight;
        this.statusNew = vehicle.statusNew;
    }
}
