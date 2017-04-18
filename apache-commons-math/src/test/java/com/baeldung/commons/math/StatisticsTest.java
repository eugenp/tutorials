package com.baeldung.commons.math;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {

    private double[] values;
    private DescriptiveStatistics descriptiveStatistics;

    @Before
    public void setUp() {
        values = new double[] {65, 51 , 16, 11 , 6519, 191 ,0 , 98, 19854, 1, 32};

        descriptiveStatistics = new DescriptiveStatistics();
        for(double v : values) {
            descriptiveStatistics.addValue(v);
        }
    }

    @Test
    public void testMean() {
        System.out.println("Mean: " + descriptiveStatistics.getMean());
    }

    @Test
    public void testMedian() {
        System.out.println("Median: " + descriptiveStatistics.getPercentile(50));
    }

    @Test
    public void testStandardDeviation() {
        System.out.println("Standard deviation: " + descriptiveStatistics.getStandardDeviation());
    }

}
