package com.baeldung.commons.math;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class StatisticsExample {

    public void run() {
        final double[] values = new double[] {65, 51 , 16, 11 , 6519, 191 ,0 , 98, 19854, 1, 32};

        final DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
        for(double v : values) {
            descriptiveStatistics.addValue(v);
        }

        System.out.println("Mean: " + descriptiveStatistics.getMean());
        System.out.println("Median: " + descriptiveStatistics.getPercentile(50));
        System.out.println("Standard deviation: " + descriptiveStatistics.getStandardDeviation());
    }

}
