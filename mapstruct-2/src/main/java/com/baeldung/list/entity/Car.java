package com.baeldung.list.entity;

public class Car {
    private String make;
    private String model;
    private int year;
    private int seats;

    private String plant1;
    private String plant1Loc;
    private String plant2;
    private String plant2Loc;

    public Car(String make, String model, int year, int seats) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.seats = seats;
    }

    public String getPlant2() {
        return plant2;
    }

    public void setPlant2(String plant2) {
        this.plant2 = plant2;
    }

    public String getPlant2Loc() {
        return plant2Loc;
    }

    public void setPlant2Loc(String plant2Loc) {
        this.plant2Loc = plant2Loc;
    }

    public String getPlant1Loc() {
        return plant1Loc;
    }

    public void setPlant1Loc(String plant1Loc) {
        this.plant1Loc = plant1Loc;
    }

    public String getPlant1() {
        return plant1;
    }

    public void setPlant1(String plant1) {
        this.plant1 = plant1;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }
}
