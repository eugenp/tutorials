package com.baeldung.math;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.math.ga.ant_colony.AntColonyOptimization;

public class AntColonyOptimizationLongRunningUnitTest {

    @Test
    public void testGenerateRandomMatrix() {
        AntColonyOptimization antTSP = new AntColonyOptimization(5);
        Assert.assertNotNull(antTSP.generateRandomMatrix(5));
    }

    @Test
    public void testStartAntOptimization() {
        AntColonyOptimization antTSP = new AntColonyOptimization(5);
        Assert.assertNotNull(antTSP.solve());
    }

}
