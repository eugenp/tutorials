package com.baeldung.switchpatterns;

public class HandlingNullValues {

    static double getDoubleUsingSwitchNullCase(Object o) {
        return switch (o) {
            case String s -> Double.parseDouble(s);
            case null -> 0d;
            default -> 0d;
        };
    }

    static double getDoubleUsingSwitchTotalType(Object o) {
        return switch (o) {
            case String s -> Double.parseDouble(s);
            case Object ob -> 0d;
        };
    }

}
