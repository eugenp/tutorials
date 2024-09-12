package com.baeldung.math.multiplicationoverflow;

public class OverflowCheck {

    public static String checkMultiplication(int a, int b) {
        try {
            int result = Math.multiplyExact(a, b);
            return "Result (int): " + result;
        } catch (ArithmeticException e) {
            return "Overflow occurred for int!";
        }
    }

    public static String checkMultiplication(long a, long b) {
        try {
            long result = Math.multiplyExact(a, b);
            return "Result (long): " + result;
        } catch (ArithmeticException e) {
            return "Overflow occurred for long!";
        }
    }
}
