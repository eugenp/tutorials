package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.domain.Car;
import org.springframework.stereotype.Service;

@Service("racingCar")
public class RacingCarService implements CarService {

    private Car car;

    public RacingCarService(Car car) {
        this.car = car;
    }

    @Override
    public void start() {
        car.start();
        System.out.println("Racing car is started");
    }

    @Override
    public void stop() {
        car.stop();
        System.out.println("Racing car is stopped");
    }

}
