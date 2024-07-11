package com.baeldung.copies.shallow;

class Engine implements Cloneable {

    private final int numberOfCylinders;
    private final int power;
    private final int capacity;

    public Engine(int numberOfCylinders, int power, int capacity) {
        this.numberOfCylinders = numberOfCylinders;
        this.power = power;
        this.capacity = capacity;
    }

    public int getNumberOfCylinders() {
        return numberOfCylinders;
    }

    public int getPower() {
        return power;
    }

    public int getCapacity() {
        return capacity;
    }

}
