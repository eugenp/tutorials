package com.baeldung.constantspatterns;

public class GeometryCalculator implements CalculatorConstants {
    public static final double UPPER_LIMIT = 100000000000000000000.0;

    public double operateOnTwoNumbers(double numberOne, double numberTwo, Operation operation) {
        if (numberOne > UPPER_LIMIT) {
            throw new IllegalArgumentException("'numberOne' is too large");
        }
        if (numberTwo > UPPER_LIMIT) {
            throw new IllegalArgumentException("'numberTwo' is too large");
        }
        double answer = 0;

        switch (operation) {
        case ADD:
            answer = numberOne + numberTwo;
            break;
        case SUBTRACT:
            answer = numberOne - numberTwo;
            break;
        case DIVIDE:
            answer = numberOne / numberTwo;
            break;
        case MULTIPLY:
            answer = numberOne * numberTwo;
            break;
        }

        return answer;
    }
}
