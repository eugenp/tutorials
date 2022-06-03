package com.baeldung.nth.root.calculator;

public class NthRootCalculator {

    public double calculateWithRound(double base, double n) {
        return Math.round(calculate(base, n));
    }

    public double calculate(double base, double n) {
        return Math.pow(base, 1.0 / n);
    }
}
