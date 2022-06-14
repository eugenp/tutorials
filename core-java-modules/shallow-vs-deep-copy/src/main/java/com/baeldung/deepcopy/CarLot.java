package com.baeldung.deepcopy;

public class CarLot {
    private Car[] cars;

    public CarLot(Car[] cars) {
        this.cars = cars;
    }

    public CarLot(CarLot lot) {
        this.cars = new Car[lot.getCars().length];
        for (int i = 0; i < lot.getCars().length; i++) {
            this.cars[i] = lot.getCars()[i];
        }
    }

    public Car[] getCars() {
        return cars;
    }

    public void setCars(Car[] cars) {
        this.cars = cars;
    }
}