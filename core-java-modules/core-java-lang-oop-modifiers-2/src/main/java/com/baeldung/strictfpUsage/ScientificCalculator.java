package com.baeldung.strictfpUsage;

public strictfp class ScientificCalculator {

    public double sum(double value1, double value2) {
        return value1 + value2;
    }

    public double diff(double value1, double value2) {
        return value1 - value2;
    }
}
