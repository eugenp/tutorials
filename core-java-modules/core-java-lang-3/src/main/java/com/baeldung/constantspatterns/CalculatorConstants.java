package com.baeldung.constantspatterns;

public interface CalculatorConstants {
    double PI = 3.14159265359;
    double UPPER_LIMIT = 0x1.fffffffffffffP+1023;

    enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    };
}
