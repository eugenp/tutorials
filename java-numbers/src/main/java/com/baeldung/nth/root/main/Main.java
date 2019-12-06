package com.baeldung.nth.root.main;

import com.baeldung.nth.root.calculator.NthRootCalculator;

public class Main {
    public static void main(String[] args) {
        NthRootCalculator calculator = new NthRootCalculator();
        Double base = Double.parseDouble(args[0]);
        Double n = Double.parseDouble(args[1]);
        Double result = calculator.calculateWithRound(base, n);
        System.out.println("The " + n + " root of " + base + " equals to " + result + ".");
    }
}
