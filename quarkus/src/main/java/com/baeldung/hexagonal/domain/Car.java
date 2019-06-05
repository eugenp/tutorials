package com.baeldung.hexagonal.domain;

/**
 * Car porgraming representation
 */
public class Car {

    private Long carId;

    private String brand;

    private String model;

    public Car(Long carId, String brand, String model) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
