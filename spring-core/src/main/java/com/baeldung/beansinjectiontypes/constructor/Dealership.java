package com.baeldung.beansinjectiontypes.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beansinjectiontypes.Car;

@Component(value="constructorDealership")
public class Dealership {
    private Car car;

    @Autowired
    public Dealership(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}
