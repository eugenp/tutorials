package com.baeldung.doubletolong;

import org.junit.Assert;
import org.junit.Test;

public class DoubleToLongUnitTest {

    final static double VALUE = 9999.999;

    @Test
    public void givenDoubleValue_whenLongValueCalled_thenLongValueReturned() {
        Assert.assertEquals(9999L, Double.valueOf(VALUE).longValue());
    }

    @Test
    public void givenDoubleValue_whenMathRoundUsed_thenLongValueReturned() {
        Assert.assertEquals(10000L, Math.round(VALUE));
    }

    @Test
    public void givenDoubleValue_whenMathRoundUsed_thenRoundDown() {
        Assert.assertEquals(9999L, Math.round(9999.444));
    }

    @Test
    public void givenDoubleValue_whenMathCeilUsed_thenLongValueReturned() {
        Assert.assertEquals(10000L, Math.ceil(VALUE), 0);
    }

    @Test
    public void givenDoubleValue_whenMathCeilUsed_thenSameValueReturned() {
        Assert.assertEquals(9999L, Math.ceil(9999.0), 0);
    }

    @Test
    public void givenDoubleValue_whenMathCeilUsed_thenDifferentThanRound() {
        Assert.assertEquals(10000L, Math.ceil(9999.444), 0);
    }

    @Test
    public void givenDoubleValue_whenMathFloorUsed_thenLongValueReturned() {
        Assert.assertEquals(9999L, Math.floor(VALUE), 0);
    }

    @Test
    public void givenDoubleValue_whenMathFloorUsed_thenSameValueReturned() {
        Assert.assertEquals(9999L, Math.floor(9999.0), 0);
    }

    @Test
    public void givenDoubleValue_whenTypeCasted_thenLongValueReturned() {
        Assert.assertEquals(9999L, (long) VALUE);
    }
}
