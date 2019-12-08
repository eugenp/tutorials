package com.baeldung.doubleConvert.vanilla;

public class VanillaCheckAndConvert {

    public static double checkedDouble(String value) {
        return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
    }

    public static double checkedDoubleDefault(String value, double defaultValue) {
        return value == null || value.isEmpty() ? defaultValue : Double.parseDouble(value);
    }

}
