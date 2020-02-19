package com.baeldung.hexagonalarchitecture.application.core;

import com.baeldung.hexagonalarchitecture.application.ports.CalculatorPort;
import com.baeldung.hexagonalarchitecture.application.ports.LoggerPort;

public class AreaCalculator implements CalculatorPort {
    private LoggerPort logger;

    public AreaCalculator(LoggerPort injectedLogger) {
        logger = injectedLogger;
    }

    @Override
    public double triangleAreaBySides(double A, double B, double C) {
        double sidesSum = (A + B + C) / 2;
        double area = Math.sqrt((sidesSum * (sidesSum - A)) * (sidesSum * (sidesSum - B)) * (sidesSum * (sidesSum - C)));
        logger.writeLog("The triangle area is: " + area);
        return (area);
    }

    @Override
    public double squareArea(double base, double height) {
        double area = base * height;
        logger.writeLog("The square area is: " + area);
        return (area);
    }
}