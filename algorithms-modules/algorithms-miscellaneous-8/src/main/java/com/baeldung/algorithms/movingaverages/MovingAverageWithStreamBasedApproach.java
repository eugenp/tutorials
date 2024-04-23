package com.baeldung.algorithms.movingaverages;

import java.util.stream.DoubleStream;

public class MovingAverageWithStreamBasedApproach {
    private int windowSize;

    public MovingAverageWithStreamBasedApproach(int windowSize) {
        this.windowSize = windowSize;
    }
    public double calculateAverage(double[] data) {
        return DoubleStream.of(data)
                .skip(Math.max(0, data.length - windowSize))
                .limit(Math.min(data.length, windowSize))
                .summaryStatistics()
                .getAverage();
    }
}
