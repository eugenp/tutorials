package com.baeldung.functionpointer;

public class AddCommand implements MathOperation {

    @Override
    public int operate(int a, int b) {
        return a + b;
    }
}