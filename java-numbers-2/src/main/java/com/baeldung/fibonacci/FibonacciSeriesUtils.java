package com.baeldung.fibonacci;

public class FibonacciSeriesUtils {

    public static int nthFibonacciTermRecursiveMethod(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        return nthFibonacciTermRecursiveMethod(n - 1) + nthFibonacciTermRecursiveMethod(n - 2);
    }

    public static int nthFibonacciTermIterativeMethod(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int n0 = 0, n1 = 1;
        int tempNthTerm;
        for (int i = 2; i <= n; i++) {
            tempNthTerm = n0 + n1;
            n0 = n1;
            n1 = tempNthTerm;
        }
        return n1;
    }

    public static int nthFibonacciTermUsingGoldenRatio(int n) {
        double goldenRatio = 1.6180339;
        int first5FibonacciTerms[] = { 0, 1, 1, 2, 3, 5 };
        if (n < 6) {
            return first5FibonacciTerms[n];
        }

        int term = 5;
        int sn = 5;
        while (term < n) {
            sn = (int)Math.round(sn * goldenRatio);
            term++;
        }
        return sn;
    }

}
