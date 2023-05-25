package com.baeldung.algorithms.gradientdescent;

import java.util.function.Function;

public class GradientDescent {

    private final double precision = 0.000001;

    public double findLocalMinimum(Function<Double, Double> f, double initialX) {
        double stepCoefficient = 0.1;
        double previousStep = 1.0;
        double currentX = initialX;
        double previousX = initialX;
        double previousY = f.apply(previousX);
        int iter = 100;

        currentX += stepCoefficient * previousY;

        while (previousStep > precision && iter > 0) {
            iter--;
            double currentY = f.apply(currentX);
            if (currentY > previousY) {
                stepCoefficient = -stepCoefficient / 2;
            }
            previousX = currentX;
            currentX += stepCoefficient * previousY;
            previousY = currentY;
            previousStep = StrictMath.abs(currentX - previousX);
        }
        return currentX;
    }

}
