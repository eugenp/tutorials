package com.baeldung.nth.root.calculator;

public class NthRootCalculator
{
    public Double calculate(Double base, Double n) {
        return Math.pow(Math.E, Math.log(base)/n);
    }
}
