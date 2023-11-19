package com.baeldung.algorithms;



import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.baeldung.algorithms.ga.binary.SimpleGeneticAlgorithm;

class BinaryGeneticAlgorithmLongRunningUnitTest {

    @Test
    void testGA() {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        assertTrue(ga.runAlgorithm(50, "1011000100000100010000100000100111001000000100000100000000001111"));
    }

}
