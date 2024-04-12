package com.baeldung.math.pascaltriangle;

public class PascalTriangle {

    public static int factorial(int i) {
        if (i == 0) {
            return 1;
        }
        return i * factorial(i - 1);
    }

    private static void printUseRecursion(int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n - i; j++) {
                System.out.print(" ");
            }

            for (int k = 0; k <= i; k++) {
                System.out.print(" " + factorial(i) / (factorial(i - k) * factorial(k)));
            }

            System.out.println();
        }
    }

public static void printUseBinomialExpansion(int n) {
    for (int line = 1; line <= n; line++) {
        for (int j = 0; j <= n - line; j++) {
            System.out.print(" ");
        }

        int k = 1;
        for (int i = 1; i <= line; i++) {
            System.out.print(k + " ");
            k = k * (line - i) / i;
        }

        System.out.println();
    }
}

    public static void main(String[] args) {
        int n = 5;
        printUseRecursion(n);
//        printUseBinomialExpansion(n);
    }

}
