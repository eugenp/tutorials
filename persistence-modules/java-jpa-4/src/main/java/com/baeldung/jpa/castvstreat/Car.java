package com.baeldung.jpa.castvstreat;

import jakarta.persistence.Entity;

@Entity
public class Car extends Vehicle {
    private Integer numberOfDoors;

    public Integer getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(Integer numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }
}
