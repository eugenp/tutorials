package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.core.domain.Car;

import java.util.List;

public interface CarServicePort {
    void createCar(Car car);

    Car getCar(int id);

    List getCars();
}