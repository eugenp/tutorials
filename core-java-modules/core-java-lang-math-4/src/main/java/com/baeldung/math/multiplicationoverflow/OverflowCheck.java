package com.baeldung.math.multiplicationoverflow;

public class OverflowCheck {

    // Check if multiplication of two ints causes overflow and return a boolean
    public static boolean checkMultiplication(int a, int b) {
        try {
            Math.multiplyExact(a, b);
            return true; // No overflow
        } catch (ArithmeticException e) {
            return false; // Overflow occurred
        }
    }

    // Check if multiplication of two longs causes overflow and return a boolean
    public static boolean checkMultiplication(long a, long b) {
        try {
            Math.multiplyExact(a, b);
            return true; // No overflow
        } catch (ArithmeticException e) {
            return false; // Overflow occurred
        }
    }
}
