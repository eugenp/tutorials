package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.domain.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("familyCar")
public class FamilyCarService implements CarService {

    private Car car;

    @Autowired
    public FamilyCarService(Car car) {
        this.car = car;
    }

    @Override
    public void start() {
        car.start();
        System.out.println("Family car is started");
    }

    @Override
    public void stop() {
        car.start();
        System.out.println("Family car is stopped");
    }

}
