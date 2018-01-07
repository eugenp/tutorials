package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.repository.CarRepository;

public class CarService {

    private CarRepository carRepository;
    private String optionalDependency;

    public CarService() {
    }

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void setOptionalDependency(String optionalDependency) {
        this.optionalDependency = optionalDependency;
    }

    // standard setters and getters

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public String getOptionalDependency() {
        return optionalDependency;
    }

}
