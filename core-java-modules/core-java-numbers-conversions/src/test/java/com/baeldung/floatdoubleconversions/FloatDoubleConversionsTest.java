package com.baeldung.floatdoubleconversions;

import org.junit.Assert;
import org.junit.Test;

public class FloatDoubleConversionsTest {

    @Test
    public void whenDoubleType_thenFloatTypeSuccess(){
        double interestRatesYearly = 13.333333333333334;
        float interest = (float) interestRatesYearly;
        Assert.assertEquals(13.333333f, interest, 0.000004f);

        Double monthlyRates = 2.111111111111112;
        float rates = monthlyRates.floatValue();
        Assert.assertEquals(2.1111112f, rates, 0.00000013);
    }
    @Test
    public void whenFloatType_thenDoubleTypeSuccess(){
        float gradeAverage =2.05f;
        double average = gradeAverage;
        Assert.assertEquals(2.05, average, 0.06);

        Float monthlyRates = 2.1111112f;
        Double rates = monthlyRates.doubleValue();
        Assert.assertEquals(2.11111112, rates, 0.0000002);//true
    }

}
