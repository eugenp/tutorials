package com.baeldung.math.multiplicationoverflow;

public class OverflowCheck {

    public static void main(String[] args) {
        int intA = 1_000_000;
        int intB = 3_000;
        checkMultiplication(intA, intB);

        long longA = 1_000_000_000L;
        long longB = 10_000_000L;
        checkMultiplication(longA, longB);
    }

    public static void checkMultiplication(int a, int b) {
        try {
            int result = Math.multiplyExact(a, b);
            System.out.println("Result (int): " + result);
        } catch (ArithmeticException e) {
            System.out.println("Overflow occurred for int!");
        }
    }

    public static void checkMultiplication(long a, long b) {
        try {
            long result = Math.multiplyExact(a, b);
            System.out.println("Result (long): " + result);
        } catch (ArithmeticException e) {
            System.out.println("Overflow occurred for long!");
        }
    }
}
