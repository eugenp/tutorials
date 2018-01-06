package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.model.Car;
import com.baeldung.beaninjection.repository.CarRepository;

public class CarService {

    private CarRepository carRepository;
    private String optionalDependency;
    private Car car;

    public CarService() {
    }

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void setOptionalDependency(String optionalDependency) {
        this.optionalDependency = optionalDependency;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    // standard setters and getters

    public CarRepository getCarRepository() {
        return carRepository;
    }

    public Car getCar() {
        return car;
    }

    public String getOptionalDependency() {
        return optionalDependency;
    }

}
