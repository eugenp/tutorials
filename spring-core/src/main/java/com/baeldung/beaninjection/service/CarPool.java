package com.baeldung.beaninjection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CarPool {

    private CarService carService;

    @Autowired
    public CarPool(@Qualifier("racingCar") CarService carService) {
        this.carService = carService;
    }

    public void startOneCar() {
        carService.start();
    }

    public void stopOneCar() {
        carService.stop();
    }

}
