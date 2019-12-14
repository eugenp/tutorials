package com.baeldung.doubletolong;

import org.junit.Assert;
import org.junit.Test;

public class DoubleToLongUnitTest {

    final static double VALUE = 9999.999;

    @Test
    public void using_longValue() {
        Assert.assertEquals(9999L, Double.valueOf(VALUE)
            .longValue());
    }

    @Test
    public void using_Math_Round() {
        Assert.assertEquals(10000L, Math.round(VALUE));
    }

    @Test
    public void using_Math_Ceil() {
        Assert.assertEquals(10000L, Math.ceil(VALUE), 0);
    }

    @Test
    public void using_Math_Floor() {
        Assert.assertEquals(9999L, Math.floor(VALUE), 0);
    }

    @Test
    public void using_Type_Cast() {
        Assert.assertEquals(9999L, (long) VALUE);
    }

}
