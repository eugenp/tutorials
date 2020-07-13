package com.baeldung.architecture.hexagonal.core.impl;

import com.baeldung.architecture.hexagonal.core.domain.Car;
import com.baeldung.architecture.hexagonal.port.CarRepositoryPort;
import com.baeldung.architecture.hexagonal.port.CarServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService implements CarServicePort {
    @Autowired
    private CarRepositoryPort carRepository;

    @Override
    public void createCar(Car car) {
        carRepository.createCar(car);
    }

    @Override
    public Car getCar(int id) {
        return carRepository.getCar(id);
    }

    @Override
    public List<Car> getCars() {
        return carRepository.getCars();
    }
}
