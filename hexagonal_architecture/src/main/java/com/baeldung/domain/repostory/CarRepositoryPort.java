package com.baeldung.domain.repostory;

import java.util.List;

import com.baeldung.domain.Car;

public interface CarRepositoryPort {

    void saveCar(String brand, Integer year);

    List<Car> listAllCars();
}