package com.cars.app.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Car getCarSample1() {
        return new Car().id(1L);
    }

    public static Car getCarSample2() {
        return new Car().id(2L);
    }

    public static Car getCarRandomSampleGenerator() {
        return new Car().id(longCount.incrementAndGet());
    }
}
