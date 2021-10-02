package com.baeldung.switchpatterns;

public class TypePatterns {

    public static double getDoubleValueUsingIf(Object o) {
        double result;

        if (o instanceof Long) {
            result = ((Long) o).doubleValue();
        } else if (o instanceof Integer) {
            result = ((Integer) o).doubleValue();
        } else if (o instanceof Float) {
            result = ((Float) o).doubleValue();
        } else if (o instanceof String) {
            result = Double.parseDouble(((String) o));
        } else {
            result = 0d;
        }

        return result;
    }

    public static double getDoubleValueUsingSwitch(Object o) {
        return switch (o) {
            case Long l -> l.doubleValue();
            case Integer i -> i.doubleValue();
            case Float f -> f.doubleValue();
            case String s -> Double.parseDouble(s);
            default -> 0d;
        };
    }

}
