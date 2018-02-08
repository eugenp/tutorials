package com.baeldung.beansinjectiontypes;

public class DealershipTraditionalStyle {
    private Car car = new Sedan();

    public Car getCar() {
        return car;
    }
}
