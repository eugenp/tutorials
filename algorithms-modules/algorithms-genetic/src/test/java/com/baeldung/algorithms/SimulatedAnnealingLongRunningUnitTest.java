package com.baeldung.algorithms;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.ga.annealing.SimulatedAnnealing;

class SimulatedAnnealingLongRunningUnitTest {

    @Test
    void testSimulateAnnealing() {
        assertTrue(SimulatedAnnealing.simulateAnnealing(10, 1000, 0.9) > 0);
    }

}
