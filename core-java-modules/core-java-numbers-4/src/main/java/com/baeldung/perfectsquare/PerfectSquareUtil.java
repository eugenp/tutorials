package com.baeldung.perfectsquare;

public class PerfectSquareUtil {

    public static boolean isPerfectSquareByUsingSqrt(long n) {
        if (n <= 0)
            return false;
        double perfectSquare = Math.sqrt(n);
        long tst = (long)(perfectSquare + 0.5);
        return tst*tst == n;
    }

    public static boolean isPerfectSquareByUsingBinarySearch(long low, long high, long number) {
        long check = (low + high) / 2L;
        if (high < low)
            return false;
        if (number == check * check) {
            return true;
        } else if (number < check * check) {
            high = check - 1L;
            return isPerfectSquareByUsingBinarySearch(low, high, number);
        } else {
            low = check + 1L;
            return isPerfectSquareByUsingBinarySearch(low, high, number);
        }
    }

    public static boolean isPerfectSquareByUsingNewtonMethod(long n) {
        long x1 = n;
        long x2 = 1L;
        while (x1 > x2) {
            x1 = (x1 + x2) / 2L;
            x2 = n / x1;
        }
        return x1 == x2 && n % x1 == 0L;
    }

    public static boolean isPerfectSquareWithOptimization(long n) {
        if (n < 0)
            return false;
        switch ((int) (n & 0xF)) {
            case 0: case 1: case 4: case 9:
                long tst = (long) Math.sqrt(n);
                return tst * tst == n;
            default:
                return false;
        }
    }
}
