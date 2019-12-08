package com.baeldung.doubleConvert.java8;

import java.util.Optional;

public class OptionalCheckAndConvert {

    public static Optional<Double> checkedDoubleOptional(String value) {
        return value == null || value.isEmpty() ? Optional.empty() : Optional.of(Double.valueOf(value));
    }
}