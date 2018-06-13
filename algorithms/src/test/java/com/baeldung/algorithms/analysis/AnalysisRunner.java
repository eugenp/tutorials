package com.baeldung.algorithms.analysis;

public class AnalysisRunner {

    public static void main(String[] args) {

        int n = 10;

        System.out.println("**** n = " + n + " ****");
        System.out.println();

        // Constant Time
        System.out.println("**** Constant time ****");

        System.out.println("Hey - your input is: " + n);
        System.out.println("Running time not dependent on input size!");
        System.out.println();
        // Logarithmic Time
        System.out.println("**** Logarithmic Time ****");
        for (int i = 1; i < n; i = i * 2) {
            // System.out.println("Hey - I'm busy looking at: " + i);
        }

        // Linear Time
        int total = 0;
        for (int i = 0; i < n; i++) {
            // System.out.println("Hey - I'm busy looking at: " + i);
            total++;
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();

        // N Log N Time
        System.out.println("**** nlogn Time ****");
        total = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < n; j = j * 2) {
                // System.out.println("Hey - I'm busy looking at: " + i + " and " + j);
                total++;
            }
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();

        // Quadratic Time
        System.out.println("**** Quadratic Time ****");
        total = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // System.out.println("Hey - I'm busy looking at: " + i + " and " + j);
                total++;
            }
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();

        // Cubic Time
        System.out.println("**** Cubic Time ****");
        total = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    // System.out.println("Hey - I'm busy looking at: " + i + " and " + j + " and " + k);
                    total++;
                }
            }
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();

        // Exponential Time
        System.out.println("**** Exponential Time ****");
        total = 0;
        for (int i = 1; i <= Math.pow(2, n); i++) {
            // System.out.println("Hey - I'm busy looking at: " + i);
            total++;
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();

        // Factorial Time
        System.out.println("**** Factorial Time ****");
        total = 0;
        for (int i = 1; i <= factorial(n); i++) {
            // System.out.println("Hey - I'm busy looking at: " + i);
            total++;
        }
        System.out.println("Total amount of times run: " + total);
    }

    static int factorial(int n) {
        if (n == 0 || n == 1)
            return 1;
        else
            return n * factorial(n - 1);
    }
}
