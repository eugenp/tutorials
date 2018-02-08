package com.baeldung.beansinjectiontypes.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beansinjectiontypes.Car;

@Component(value="setterDealership")
public class Dealership {
    private Car car;

    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }
    public Car getCar() {
        return car;
    }
}
