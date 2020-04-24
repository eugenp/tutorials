package com.baeldung.r;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.script.ScriptException;

import org.junit.Test;

import org.junit.Assert;

/**
 * Test for {@link RenjinMean}.
 * 
 * @author Donato Rimenti
 */
public class RenjinMeanUnitTest {

    /**
     * Object to test.
     */
    private RenjinMean renjinMean = new RenjinMean();

    /**
    * Test for {@link RenjinMeanUnitTest#mean(int[])}.
    * 
    * @throws ScriptException    if an error occurs
    * @throws URISyntaxException if an error occurs
    * @throws IOException        if an error occurs
    */
    @Test
    public void givenValues_whenMean_thenCorrect() throws IOException, URISyntaxException, ScriptException {
        int[] input = { 1, 2, 3, 4, 5 };
        double result = renjinMean.mean(input);
        Assert.assertEquals(3.0, result, 0.000001);
    }
}