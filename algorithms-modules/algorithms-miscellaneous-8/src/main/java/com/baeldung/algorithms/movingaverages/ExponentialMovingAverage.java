package com.baeldung.algorithms.movingaverages;

public class ExponentialMovingAverage {

    private final double alpha;
    private Double previousEMA;

    public ExponentialMovingAverage(double alpha) {
        if (alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be in the range (0, 1]");
        }
        this.alpha = alpha;
        this.previousEMA = null;
    }

    public double calculateEMA(double newValue) {
        if (previousEMA == null) {
            previousEMA = newValue;
        } else {
            previousEMA = alpha * newValue + (1 - alpha) * previousEMA;
        }
        return previousEMA;
    }
}
