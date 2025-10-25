package com.baeldung.padovan;

public class PadovanSeriesUtils {

    public static int nthPadovanTermRecursiveMethod(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        return nthPadovanTermRecursiveMethod(n - 2) + nthPadovanTermRecursiveMethod(n - 3);
    }

    public static int nthPadovanTermIterativeMethod(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        int p0 = 1, p1 = 1, p2 = 1;
        int tempNthTerm;
        for (int i = 3; i <= n; i++) {
            tempNthTerm = p0 + p1;
            p0 = p1;
            p1 = p2;
            p2 = tempNthTerm;
        }
        return p2;
    }

    public static int nthPadovanTermUsingFormula(int n) {
        if (n == 0 || n == 1 || n == 2) {
            return 1;
        }
        // Padovan spiral can be approximated using formula
        // More complex than Fibonacci's Binet formula
        double p = Math.pow(1.32471795724474602596, n - 1);
        return (int) Math.round(p / 1.045356793252532962);
    }
}

