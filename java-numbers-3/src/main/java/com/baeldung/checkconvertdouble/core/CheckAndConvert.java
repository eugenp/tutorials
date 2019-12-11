package com.baeldung.checkconvertdouble.core;

import java.util.Optional;

public class CheckAndConvert {

    public static Optional<Double> checkedDoubleOptional(String value) {
        return value == null || value.isEmpty() ? Optional.empty() : Optional.of(Double.valueOf(value));
    }

    public static double checkedDouble(String value) {
        return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
    }

    public static double checkedDoubleDefault(String value, double defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : Double.parseDouble(value);
    }
}