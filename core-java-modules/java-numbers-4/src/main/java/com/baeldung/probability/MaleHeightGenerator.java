package com.baeldung.probability;

import org.apache.commons.math3.distribution.NormalDistribution;

public class MaleHeightGenerator {
    private static final double MEAN_HEIGHT = 176.02;
    private static final double STANDARD_DEVIATION = 7.11;
    private static NormalDistribution distribution =  new NormalDistribution(MEAN_HEIGHT, STANDARD_DEVIATION);

    public static double generateNormalHeight() {
        return distribution.sample();
    }

    public static double probabilityOfHeightBetween(double heightLowerExclusive, double heightUpperInclusive) {
        return distribution.probability(heightLowerExclusive, heightUpperInclusive);
    }
}
