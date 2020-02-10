package com.baeldung.jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.jpa.entity.Car;
import com.baeldung.jpa.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public int getTotalCarsByModel(String model) {
        return carRepository.getTotalCarsByModel(model);
    }

    public List<Car> findCarsAfterYear(Integer year) {
        return carRepository.findCarsAfterYear(year);
    }
}
