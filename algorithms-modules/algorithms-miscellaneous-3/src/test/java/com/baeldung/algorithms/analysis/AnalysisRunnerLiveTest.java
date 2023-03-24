package com.baeldung.algorithms.analysis;

import org.junit.jupiter.api.Test;

class AnalysisRunnerLiveTest {

    int n = 10;
    int total = 0;

    @Test
    void whenConstantComplexity_thenConstantRuntime() {

        System.out.println("**** n = " + n + " ****");
        System.out.println();

        // Constant Time
        System.out.println("**** Constant time ****");

        System.out.println("Hey - your input is: " + n);
        System.out.println("Running time not dependent on input size!");
        System.out.println();
    }

    @Test
    void whenLogarithmicComplexity_thenLogarithmicRuntime() {
        // Logarithmic Time
        System.out.println("**** Logarithmic Time ****");
        for (int i = 1; i < n; i = i * 2) {
            // System.out.println("Hey - I'm busy looking at: " + i);
            total++;
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();
    }

    @Test
    void whenLinearComplexity_thenLinearRuntime() {
        // Linear Time
        System.out.println("**** Linear Time ****");
        for (int i = 0; i < n; i++) {
            // System.out.println("Hey - I'm busy looking at: " + i);
            total++;
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();

    }

    @Test
    void whenNLogNComplexity_thenNLogNRuntime() {
        // N Log N Time
        System.out.println("**** nlogn Time ****");
        total = 0;
        for (

            int i = 1; i <= n; i++) {
            for (int j = 1; j < n; j = j * 2) {
                // System.out.println("Hey - I'm busy looking at: " + i + " and " + j);
                total++;
            }
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();
    }

    @Test
    void whenQuadraticComplexity_thenQuadraticRuntime() {
        // Quadratic Time
        System.out.println("**** Quadratic Time ****");
        total = 0;
        for (

            int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // System.out.println("Hey - I'm busy looking at: " + i + " and " + j);
                total++;
            }
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();
    }

    @Test
    void whenCubicComplexity_thenCubicRuntime() {
        // Cubic Time
        System.out.println("**** Cubic Time ****");
        total = 0;
        for (

            int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k <= n; k++) {
                    // System.out.println("Hey - I'm busy looking at: " + i + " and " + j + " and " + k);
                    total++;
                }
            }
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();
    }

    @Test
    void whenExponentialComplexity_thenExponentialRuntime() {
        // Exponential Time
        System.out.println("**** Exponential Time ****");
        total = 0;
        for (

            int i = 1; i <= Math.pow(2, n); i++) {
            // System.out.println("Hey - I'm busy looking at: " + i);
            total++;
        }
        System.out.println("Total amount of times run: " + total);
        System.out.println();
    }

    @Test
    void whenFactorialComplexity_thenFactorialRuntime() {
        // Factorial Time
        System.out.println("**** Factorial Time ****");
        total = 0;
        for (

            int i = 1; i <=

            factorial(n); i++) {
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
