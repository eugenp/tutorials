package com.baeldung.interface_vs_abstract_class;

public class Car extends Vehicle {

    public Car(String vechicleName) {
        super(vechicleName);
    }

    public Car(String vechicleName, String vehicleModel) {
        super(vechicleName, vehicleModel);
    }

    public Car(String vechicleName, String vehicleModel, Long makeYear) {
        super(vechicleName, vehicleModel, makeYear);
    }

    @Override
    protected void start() {
        // code implementation details on starting a car.
    }

    @Override
    protected void stop() {
        // code implementation details on stopping a car.
    }

    @Override
    protected void drive() {
        // code implementation details on start driving a car.
    }

    @Override
    protected void changeGear() {
        // code implementation details on changing the car gear.
    }

    @Override
    protected void reverse() {
        // code implementation details on reverse driving a car.
    }

}
