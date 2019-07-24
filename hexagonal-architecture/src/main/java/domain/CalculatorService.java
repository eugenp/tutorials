package domain;

import ports.Calculator;

public class CalculatorService implements Calculator {
    public int multiply(int factor1, int factor2) {
        return factor1 * factor2;
    }
}