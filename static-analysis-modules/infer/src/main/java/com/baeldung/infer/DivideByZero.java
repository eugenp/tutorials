package com.baeldung.infer;

public class DivideByZero {

    public static void main(String[] args) {
        DivideByZero.divideByZero();
    }

    private static void divideByZero() {
        int dividend = 5;
        int divisor = 0;
        int result = dividend / divisor;
    }
}
