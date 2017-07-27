package com.baeldung.neuroph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neuroph.core.NeuralNetwork;

import static org.junit.Assert.*;

public class XORTest {
    private NeuralNetwork ann = null;

    @Before
    public void annInit() {
        ann = NeurophXOR.trainNeuralNetwork(NeurophXOR.assembleNeuralNetwork());
    }

    @Test
    public void leftDisjunctTest() {
        ann.setInput(0, 1);
        ann.calculate();
        assertEquals(ann.getOutput()[0], 1.0,0.0);
    }

    @Test
    public void rightDisjunctTest() {
        ann.setInput(1, 0);
        ann.calculate();
        assertEquals(ann.getOutput()[0], 1.0,0.0);
    }

    @Test
    public void bothFalseConjunctTest() {
        ann.setInput(0, 0);
        ann.calculate();
        assertEquals(ann.getOutput()[0], 0.0,0.0);
    }

    @Test
    public void bothTrueConjunctTest() {
        ann.setInput(1, 1);
        ann.calculate();
        assertEquals(ann.getOutput()[0], 0.0,0.0);
    }

    @After
    public void annClose() {
        ann = null;
    }
}
