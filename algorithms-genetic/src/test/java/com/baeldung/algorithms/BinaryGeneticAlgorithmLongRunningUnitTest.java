package com.baeldung.algorithms;

import com.baeldung.algorithms.ga.binary.SimpleGeneticAlgorithm;
import org.junit.Assert;
import org.junit.Test;


public class BinaryGeneticAlgorithmLongRunningUnitTest {

    @Test
    public void testGA() {
        SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
        Assert.assertTrue(ga.runAlgorithm(50, "1011000100000100010000100000100111001000000100000100000000001111"));
    }

}
