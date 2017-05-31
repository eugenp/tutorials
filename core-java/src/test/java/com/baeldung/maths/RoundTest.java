package com.baeldung.maths;

import org.apache.commons.math3.util.Precision;
import org.decimal4j.util.DoubleRounder;
import org.junit.Assert;
import org.junit.Test;

public class RoundTest {
    private double value = 2.03456d;
    private int places = 2;
    private double delta = 0.0d;
    private double expected = 2.03d;

    @Test
    public void givenDecimalNumber_whenRoundToNDecimalPlaces_thenGetExpectedResult() {   
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertEquals(expected, Round.roundOptional(value, places), delta);
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertEquals(expected, DoubleRounder.round(value, places), delta);
        
        places = 3;
        expected = 2.035d;
        
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertEquals(expected, Round.roundOptional(value, places), delta);
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertEquals(expected, DoubleRounder.round(value, places), delta);
    }
}
