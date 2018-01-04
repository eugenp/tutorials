package com.baeldung.beaninjection.service;

import com.baeldung.beaninjection.domain.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("familyCar")
public class FamilyCarService implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyCarService.class);

    private Car car;

    @Autowired
    public FamilyCarService(Car car) {
        this.car = car;
    }

    @Override
    public void start() {
        car.start();
        LOG.debug("Family car is started");
    }

    @Override
    public void stop() {
        car.start();
        LOG.debug("Family car is stopped");
    }

}
