package com.baeldung.storedprocedure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.storedprocedure.entity.Car;
import com.baeldung.storedprocedure.repository.CarRepository;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public int getTotalCarsByModel(String model) {
        return carRepository.getTotalCarsByModel(model);
    }
    
    public int getTotalCarsByModelProcedureName(String model) {
        return carRepository.getTotalCarsByModelProcedureName(model);
    }
    
    public int getTotalCarsByModelValue(String model) {
        return carRepository.getTotalCarsByModelValue(model);
    }
    
    public int getTotalCarsByModelExplicit(String model) {
        return carRepository.GET_TOTAL_CARS_BY_MODEL(model);
    }
    
    public int getTotalCarsByModelEntity(String model) {
        return carRepository.getTotalCarsByModelEntiy(model);
    }

    public List<Car> findCarsAfterYear(Integer year) {
        return carRepository.findCarsAfterYear(year);
    }
}
