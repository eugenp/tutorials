package com.baeldung.perfectsquare;

public class PerfectSquareUtil {

    public static class BinarySearchRange {
        private long low;
        private long high;

        BinarySearchRange() {}

        BinarySearchRange(long low, long high) {
            this.low = low;
            this.high = high;
        }

        public long getLow() {
            return low;
        }

        public void setLow(long low) {
            this.low = low;
        }

        public long getHigh() {
            return high;
        }

        public void setHigh(long high) {
            this.high = high;
        }
    }

    static {
        initiateOptimizedBinarySearchLookupTable();
    }

    private static void initiateOptimizedBinarySearchLookupTable() {
        lookupTable.add(new BinarySearchRange());
        lookupTable.add(new BinarySearchRange(1L, 4L));
        lookupTable.add(new BinarySearchRange(3L, 10L));
        for (int i = 3; i < 20; i++) {
            lookupTable.add(new BinarySearchRange(lookupTable.get(i - 2).low * 10,
                    lookupTable.get(i - 2).high * 10));
        }
    }

    public static boolean isPerfectSquareByUsingSqrt(long n) {
        if (n <= 0)
            return false;
        double perfectSquare = Math.sqrt(n);
        long tst = (long)(perfectSquare + 0.5);
        return tst*tst == n;
    }

    public static boolean isPerfectSquareByUsingBinarySearch(long low, long high, long n) {
        long check = (low + high) / 2L;
        if (high < low)
            return false;
        if (n == check * check) {
            return true;
        } else if (n < check * check) {
            high = check - 1L;
            return isPerfectSquareByUsingBinarySearch(low, high, n);
        } else {
            low = check + 1L;
            return isPerfectSquareByUsingBinarySearch(low, high, n);
        }
    }

    public static boolean isPerfectSquareByUsingOptimizedBinarySearch(long n) {
        int numberOfDigits = Long.toString(n).length();
        return isPerfectSquareByUsingBinarySearch(lookupTable.get(numberOfDigits).low,
                lookupTable.get(numberOfDigits).high, n);
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
