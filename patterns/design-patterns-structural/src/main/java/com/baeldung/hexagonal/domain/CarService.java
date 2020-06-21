package com.baeldung.hexagonal.domain;

import java.util.List;

public class CarService implements CarPortUserSide {

    private CarPortServerSide carPortServerSide;

    public CarService(CarPortServerSide carPortServerSide) {
        this.carPortServerSide = carPortServerSide;
    }

    @Override
    public void submitCar(Car car) {
        carPortServerSide.saveCar(car);
    }

    @Override
    public List<Car> retrieveCars() {
        return carPortServerSide.getCars();
    }
}
