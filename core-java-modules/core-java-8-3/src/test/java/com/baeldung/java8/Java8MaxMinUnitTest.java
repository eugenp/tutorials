package com.baeldung.java8;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.baeldung.java_8_features.Car;

public class Java8MaxMinUnitTest {

    @Test
    public void whenArrayIsOfCustomTypeThenMaxUsesCustomComparator() {
        final Car porsche = new Car("Porsche 959", 319);
        final Car ferrari = new Car("Ferrari 288 GTO", 303);
        final Car bugatti = new Car("Bugatti Veyron 16.4 Super Sport", 415);
        final Car mcLaren = new Car("McLaren F1", 355);
        final Car[] fastCars = { porsche, ferrari, bugatti, mcLaren };

        final Car maxBySpeed = Arrays.stream(fastCars)
            .max(Comparator.comparing(Car::getTopSpeed))
            .orElseThrow(NoSuchElementException::new);

        assertEquals(bugatti, maxBySpeed);
    }
}
