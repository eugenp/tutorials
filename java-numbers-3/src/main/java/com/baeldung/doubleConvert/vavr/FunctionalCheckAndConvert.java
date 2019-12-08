package com.baeldung.doubleConvert.vavr;

import io.vavr.control.Try;

public class FunctionalCheckAndConvert {

    public static double checkDoubleFunctional(String value, double defaultValue) {
        return Try.of(() -> Double.parseDouble(value)).getOrElse(defaultValue);
    }

}