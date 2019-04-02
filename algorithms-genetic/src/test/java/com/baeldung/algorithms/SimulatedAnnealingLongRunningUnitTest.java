package com.baeldung.math;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.math.ga.annealing.SimulatedAnnealing;

public class SimulatedAnnealingLongRunningUnitTest {

    @Test
    public void testSimulateAnnealing() {
        Assert.assertTrue(SimulatedAnnealing.simulateAnnealing(10, 1000, 0.9) > 0);
    }

}
