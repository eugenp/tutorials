package com.baeldung.math.multiplicationoverflow;

public class PrimitiveOverflowCheck {
    public static void main(String[] args) {
        int intA = 1_000_000;
        int intB = 3_000;
        checkMultiplication(intA, intB);

        long longA = 1_000_000_000L;
        long longB = 10_000_000L;
        checkMultiplication(longA, longB);
    }

    public static void checkMultiplication(int a, int b) {
        if (willOverflow(a, b)) {
            System.out.println("Overflow occurred for int!");
        } else {
            int result = a * b;
            System.out.println("Result (int): " + result);
        }
    }

    public static void checkMultiplication(long a, long b) {
        if (willOverflow(a, b)) {
            System.out.println("Overflow occurred for long!");
        } else {
            long result = a * b;
            System.out.println("Result (long): " + result);
        }
    }

    public static boolean willOverflow(int a, int b) {
        if (a == 0 || b == 0) return false;
        if (a > 0 && b > 0 && a > Integer.MAX_VALUE / b) return true;
        if (a > 0 && b < 0 && b < Integer.MIN_VALUE / a) return true;
        if (a < 0 && b > 0 && a < Integer.MIN_VALUE / b) return true;
        return a < 0 && b < 0 && a < Integer.MAX_VALUE / b;
    }

    public static boolean willOverflow(long a, long b) {
        if (a == 0 || b == 0) return false;
        if (a > 0 && b > 0 && a > Long.MAX_VALUE / b) return true;
        if (a > 0 && b < 0 && b < Long.MIN_VALUE / a) return true;
        if (a < 0 && b > 0 && a < Long.MIN_VALUE / b) return true;
        return a < 0 && b < 0 && a < Long.MAX_VALUE / b;
    }
}