package com.baeldung.maths;

import org.apache.commons.math3.util.Precision;
import org.decimal4j.util.DoubleRounder;
import org.junit.Assert;
import org.junit.Test;

public class RoundUnitTest {
    private double value = 2.03456d;
    private int places = 2;
    private double delta = 0.0d;
    private double expected = 2.03d;

    @Test
    public void givenDecimalNumber_whenRoundToNDecimalPlaces_thenGetExpectedResult() {   
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertEquals(expected, Round.roundNotPrecise(value, places), delta);
        Assert.assertEquals(expected, Round.roundAvoid(value, places), delta);
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertEquals(expected, DoubleRounder.round(value, places), delta);
        
        places = 3;
        expected = 2.035d;
        
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertEquals(expected, Round.roundNotPrecise(value, places), delta);
        Assert.assertEquals(expected, Round.roundAvoid(value, places), delta);
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertEquals(expected, DoubleRounder.round(value, places), delta);
        
        value = 1000.0d;
        places = 17;
        expected = 1000.0d;
        
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertEquals(expected, Round.roundNotPrecise(value, places), delta);
        Assert.assertNotEquals(expected, Round.roundAvoid(value, places), delta); // Returns: 92.23372036854776 !
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertEquals(expected, DoubleRounder.round(value, places), delta);
        
        value = 256.025d;
        places = 2;
        expected = 256.03d;
        
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertNotEquals(expected, Round.roundNotPrecise(value, places), delta); // Returns: 256.02 !
        Assert.assertNotEquals(expected, Round.roundAvoid(value, places), delta); // Returns: 256.02 !
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertNotEquals(expected, DoubleRounder.round(value, places), delta); // Returns: 256.02 !
        
        value = 260.775d; 
        places = 2;
        expected = 260.78d;
        
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertNotEquals(expected, Round.roundNotPrecise(value, places), delta); // Returns: 260.77 !
        Assert.assertNotEquals(expected, Round.roundAvoid(value, places), delta); // Returns: 260.77 !
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertNotEquals(expected, DoubleRounder.round(value, places), delta); // Returns: 260.77 !
        
        value = 90080070060.1d;
        places = 9;
        expected = 90080070060.1d;
        
        Assert.assertEquals(expected, Round.round(value, places), delta);
        Assert.assertEquals(expected, Round.roundNotPrecise(value, places), delta);
        Assert.assertNotEquals(expected, Round.roundAvoid(value, places), delta); // Returns: 9.223372036854776E9 !
        Assert.assertEquals(expected, Precision.round(value, places), delta);
        Assert.assertEquals(expected, DoubleRounder.round(value, places), delta);
    }
}
