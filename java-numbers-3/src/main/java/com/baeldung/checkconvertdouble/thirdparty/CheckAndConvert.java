package com.baeldung.checkconvertdouble.thirdparty;

import io.vavr.control.Try;

public class CheckAndConvert {

    public static double checkDoubleFunctional(String value, double defaultValue) {
        return Try.of(() -> Double.parseDouble(value)).getOrElse(defaultValue);
    }

}