package com.baeldung.applications.spring.rest.dto;

import com.baeldung.domain.entities.Car;

public class CrudCarRequest {

    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
