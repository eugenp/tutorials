package com.baeldung.algorithms;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.ga.ant_colony.AntColonyOptimization;

class AntColonyOptimizationLongRunningUnitTest {

    @Test
    void testGenerateRandomMatrix() {
        AntColonyOptimization antTSP = new AntColonyOptimization(5);
        assertNotNull(antTSP.generateRandomMatrix(5));
    }

    @Test
    void testStartAntOptimization() {
        AntColonyOptimization antTSP = new AntColonyOptimization(5);
        assertNotNull(antTSP.solve());
    }

}
