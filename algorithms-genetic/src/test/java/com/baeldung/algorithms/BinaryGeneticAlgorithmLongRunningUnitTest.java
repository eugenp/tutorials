package com.baeldung.algorithms;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.algorithms.ga.binary.SimpleGeneticAlgorithm;

public class BinaryGeneticAlgorithmLongRunningUnitTest {

    @Test
    public void testGA() {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        Assert.assertTrue(ga.runAlgorithm(50, "1011000100000100010000100000100111001000000100000100000000001111"));
    }

}
