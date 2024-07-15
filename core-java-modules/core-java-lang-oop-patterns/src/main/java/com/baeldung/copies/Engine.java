package com.baeldung.copies;

public class Engine implements Cloneable {

    private final int numberOfCylinders;
    private final int power;
    private final int capacity;

    public Engine(int numberOfCylinders, int power, int capacity) {
        this.numberOfCylinders = numberOfCylinders;
        this.power = power;
        this.capacity = capacity;
    }

    public Engine(Engine other) {
        this.numberOfCylinders = other.numberOfCylinders;
        this.power = other.power;
        this.capacity = other.capacity;
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

    @Override
    public Engine clone() {
        try {
            return (Engine) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new IllegalStateException(cloneNotSupportedException);
        }
    }

}
