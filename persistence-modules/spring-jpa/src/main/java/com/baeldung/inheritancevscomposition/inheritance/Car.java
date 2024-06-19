package com.baeldung.inheritancevscomposition.inheritance;

import jakarta.persistence.Entity;

@Entity
public class Car extends Vehicle {

    private int numberOfDoors;

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
}