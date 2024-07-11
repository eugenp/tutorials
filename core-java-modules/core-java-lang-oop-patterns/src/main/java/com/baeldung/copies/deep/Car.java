package com.baeldung.copies.deep;

class Car implements Cloneable {

    private int numberOfDoors;
    private String make;
    private Engine engine;
    private FuelTank fuelTank;

    public Car(int numberOfDoors, String make, Engine engine, FuelTank fuelTank) {
        this.numberOfDoors = numberOfDoors;
        this.make = make;
        this.engine = engine;
        this.fuelTank = fuelTank;
    }

    // Deep Copy Constructor
    public Car(Car original) {
        numberOfDoors = original.numberOfDoors;
        make = new String(original.make);
        engine = new Engine(original.engine);
        fuelTank = new FuelTank(original.fuelTank);
    }

    public int getNumberOfDoors() {
        return numberOfDoors;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public FuelTank getFuelTank() {
        return this.fuelTank;
    }

    public void setFuelTank(FuelTank fuelTank) {
        this.fuelTank = fuelTank;
    }

    @Override
    protected Car clone() {
        Car car;
        try {
            car = (Car) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new IllegalStateException(cloneNotSupportedException);
        }
        car.make = new String(car.make);
        car.engine = engine.clone();
        car.fuelTank = fuelTank.clone();
        return car;
    }

}
