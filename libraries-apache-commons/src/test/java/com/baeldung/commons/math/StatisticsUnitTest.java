package com.baeldung.commons.math;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StatisticsUnitTest {

    private double[] values;
    private DescriptiveStatistics descriptiveStatistics;

    @Before
    public void setUp() {
        values = new double[] { 65, 51, 16, 11, 6519, 191, 0, 98, 19854, 1, 32 };

        descriptiveStatistics = new DescriptiveStatistics();
        for (double v : values) {
            descriptiveStatistics.addValue(v);
        }
    }

    @Test
    public void whenDescriptiveStatisticsGetMean_thenCorrect() {
        Assert.assertEquals(2439.8181818181815, descriptiveStatistics.getMean(), 1e-7);
    }

    @Test
    public void whenDescriptiveStatisticsGetMedian_thenCorrect() {
        Assert.assertEquals(51, descriptiveStatistics.getPercentile(50), 1e-7);
    }

    @Test
    public void whenDescriptiveStatisticsGetStandardDeviation_thenCorrect() {
        Assert.assertEquals(6093.054649651221, descriptiveStatistics.getStandardDeviation(), 1e-7);
    }

}
