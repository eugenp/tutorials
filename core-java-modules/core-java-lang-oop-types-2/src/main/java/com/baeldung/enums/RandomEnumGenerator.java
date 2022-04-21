package com.baeldung.enums;

import java.util.Random;

public class RandomEnumGenerator<T extends Enum<T>> {

    private static final Random PRNG = new Random();
    private final T[] values;

    public RandomEnumGenerator(Class<T> e) {
        values = e.getEnumConstants();
    }

    public T randomEnum() {
        return values[PRNG.nextInt(values.length)];
    }
}