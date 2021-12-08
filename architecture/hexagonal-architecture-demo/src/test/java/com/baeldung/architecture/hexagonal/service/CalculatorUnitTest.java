package com.baeldung.architecture.hexagonal.service;

import com.baeldung.architecture.hexagonal.cache.InMemoryCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void givenTwoNumber_whenAdded_shouldReturnTheSum() {
        Calculator calculator = new Calculator(new InMemoryCache());

        Double a = 10d;
        Double b = 10d;

        Assertions.assertEquals(20d, calculator.add(a, b), 0d);
    }
}