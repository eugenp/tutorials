package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.domain.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("racingCar")
public class RacingCarService implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(RacingCarService.class);

    private Car car;

    @Autowired
    public RacingCarService(Car car) {
        this.car = car;
    }

    @Override
    public void start() {
        car.start();
        LOG.debug("Racing car is started");
    }

    @Override
    public void stop() {
        car.stop();
        LOG.debug("Racing car is stopped");
    }

}
