package com.baeldung.math.multiplicationoverflow;

public class PrimitiveOverflowCheck {

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