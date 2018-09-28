package com.baeldung.percentage;

import org.junit.Assert;
import org.junit.Test;

public class PercentageCalculatorUnitTest {
    private PercentageCalculator pc = new PercentageCalculator();

    @Test
    public void whenPass2Integers_thenShouldCalculatePercentage(){
        Assert.assertEquals("Result not as expected",
                50.0,pc.calculatePercentage(50,100),0.1);
    }

    @Test
    public void whenPassObtainedMarksAsDouble_thenShouldCalculatePercentage(){
        Assert.assertEquals("Result not as expected",5.05,
                pc.calculatePercentage(50.5,1000),0.1);
    }

    @Test
    public void whenPassTotalMarksAsDouble_thenShouldCalculatePercentage(){
        Assert.assertEquals("Result not as expected",19.6,
                pc.calculatePercentage(5,25.5),0.1);
    }

    @Test
    public void whenPass2DoubleNumbers_thenShouldCalculatePercentage(){
        Assert.assertEquals("Result not as expected",20,
                pc.calculatePercentage(5.5,27.5),0.1);
    }

}
