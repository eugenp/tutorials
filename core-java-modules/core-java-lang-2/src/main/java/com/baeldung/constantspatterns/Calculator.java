package com.baeldung.constantspatterns;

public class Calculator {
    public static final double PI = 3.14159265359;
    private static final double UPPER_LIMIT = 0x1.fffffffffffffP+1023;

    public enum Operation {
        ADD, SUBTRACT, DIVIDE, MULTIPLY
    }

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
