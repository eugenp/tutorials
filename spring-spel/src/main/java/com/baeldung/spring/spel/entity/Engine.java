package com.baeldung.spring.spel.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("engine")
public class Engine {
    @Value("3200")
    private int capacity;
    @Value("250")
    private int horsePower;
    @Value("6")
    private int numberOfCylinders;

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getNumberOfCylinders() {
        return numberOfCylinders;
    }

    public void setNumberOfCylinders(int numberOfCylinders) {
        this.numberOfCylinders = numberOfCylinders;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "capacity=" + capacity +
                ", horsePower=" + horsePower +
                ", numberOfCylinders=" + numberOfCylinders +
                '}';
    }
}
