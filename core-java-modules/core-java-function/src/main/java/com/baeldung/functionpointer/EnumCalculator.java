package com.baeldung.functionpointer;

public class EnumCalculator {

    public int calculate(int a, int b, MathOperationEnum operation) {
        return operation.apply(a, b);
    }
}