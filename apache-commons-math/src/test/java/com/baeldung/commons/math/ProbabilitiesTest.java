package com.baeldung.commons.math;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.junit.Test;

public class ProbabilitiesTest {

    @Test
    public void testProbabilities() {
        final NormalDistribution normalDistribution = new NormalDistribution(10, 3);

        System.out.println(normalDistribution.sample());
    }

}
