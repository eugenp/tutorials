package com.baeldung.hexagonal.domain;

public class Car {

    private String licensePlate;
    private String name;

    public Car() {
    }

    public Car(String licensePlate, String name) {
        this.licensePlate = licensePlate;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
