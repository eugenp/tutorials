package com.baeldung.hexagonal.util;

import com.baeldung.hexagonal.domain.Car;
import com.baeldung.hexagonal.domain.CarPortServerSide;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter implements CarPortServerSide {

    private List<Car> cars = new ArrayList<>();

    @Override
    public void saveCar(Car car) {
        cars.add(car);
    }

    @Override
    public List<Car> getCars() {
        return cars;
    }
}