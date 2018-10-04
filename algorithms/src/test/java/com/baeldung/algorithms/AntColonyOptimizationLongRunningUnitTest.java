package com.baeldung.algorithms;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.algorithms.ga.ant_colony.AntColonyOptimization;

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
