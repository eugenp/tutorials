package com.baeldung.junit5vstestng;

public class Calculator {

    public double divide(double a, double b) {
        if (b == 0) {
            throw new DivideByZeroException("Divider cannot be equal to zero!");
        }
        return a/b;
    }

}



