package com.baeldung.hexagonalarchitecture.application.ports;

public interface CalculatorPort {
    double triangleAreaBySides(double A, double B, double C);
    double squareArea(double base, double height);
}
