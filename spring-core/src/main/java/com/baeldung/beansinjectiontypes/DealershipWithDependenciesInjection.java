package com.baeldung.beansinjectiontypes;

public class DealershipWithDependenciesInjection {
    private Car car;

    public DealershipWithDependenciesInjection(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }
}
