package com.baeldung.hexagonal.domain;

import java.util.List;

public class CarService {

    private CarPortServerSide carPortServerSide;

    public CarService(CarPortServerSide carPortServerSide) {
        this.carPortServerSide = carPortServerSide;
    }

    public void createCar(Car car) {
        carPortServerSide.saveCar(car);
    }

    public List<Car> getCars() {
        return carPortServerSide.getCars();
    }
}
