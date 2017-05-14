package com.baeldung.commons.math;

import org.apache.commons.math3.distribution.NormalDistribution;

public class ProbabilitiesExample {

    public void run() {
        final NormalDistribution normalDistribution = new NormalDistribution(10, 3);

        System.out.println(normalDistribution.sample());
    }

}
