package com.baeldung.algorithms.movingaverages;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class MovingAverageWithApacheCommonsMath {

    private final DescriptiveStatistics stats;

    public MovingAverageWithApacheCommonsMath(int windowSize) {
        this.stats = new DescriptiveStatistics(windowSize);
    }

    public void add(double value) {
        stats.addValue(value);
    }

    public double getMovingAverage() {
        return stats.getMean();
    }

}
