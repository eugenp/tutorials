package com.baeldung.suppliercallable.service.callable;

import java.util.concurrent.Callable;

public class CarDriverValidatorCallable implements Callable<Boolean> {

    private final Integer age;

    @Override
    public Boolean call() throws Exception {
        return age > 18;
    }

    public CarDriverValidatorCallable(Integer age) {
        this.age = age;
    }
}
