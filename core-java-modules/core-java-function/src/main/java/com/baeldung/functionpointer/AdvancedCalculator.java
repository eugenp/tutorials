package com.baeldung.functionpointer;

import java.util.function.BiFunction;

public class AdvancedCalculator {

    public int compute(int a, int b, BiFunction<Integer, Integer, Integer> operation) {
        return operation.apply(a, b);
    }
}
