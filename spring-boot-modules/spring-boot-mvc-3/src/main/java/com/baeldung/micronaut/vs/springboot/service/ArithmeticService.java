package com.baeldung.micronaut.vs.springboot.service;

import org.springframework.stereotype.Service;

@Service
public class ArithmeticService {
    public float add(float number1, float number2) {
        return number1 + number2;
    }

    public float subtract(float number1, float number2) {
        return number1 - number2;
    }

    public float multiply(float number1, float number2) {
        return number1 * number2;
    }

    public float divide(float number1, float number2) {
        if (number2 == 0) {
            throw new IllegalArgumentException("'number2' cannot be zero");
        }
        return number1 / number2;
    }
}
