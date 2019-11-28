package com.hex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hex.domain.Car;
import com.hex.port.CarRepositoryPort;
@Service
public class CarService {

    @Autowired
    private CarRepositoryPort carRepository;

    public void create(String model, String make, long color){
        carRepository.create(model, make, color);
    }

    public Car view(Integer carId){
        return carRepository.getCar(carId);
    }
}