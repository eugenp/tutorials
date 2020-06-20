package com.baeldung.hexagonal.server;

import com.baeldung.hexagonal.domain.CarPortServerSide;
import com.baeldung.hexagonal.domain.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the actual interface to the persistence/back-end.
 */
public class CarServerAdapter implements CarPortServerSide {

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
