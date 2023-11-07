package com.baeldung.floatdoubleconversions;

import org.junit.Assert;
import org.junit.Test;

public class FloatDoubleConversionsUnitTest {

    @Test
    public void whenDoubleType_thenFloatTypeSuccess(){
        double interestRatesYearly = 13.333333333333333;
        float interest = (float) interestRatesYearly;
        System.out.println(interest); //13.333333
        Assert.assertEquals(13.333333f, interest, 0.000001f);

        Double monthlyRates = 2.111111111111111;
        float rates = monthlyRates.floatValue();
        System.out.println(rates); //2.1111112
        Assert.assertEquals(2.1111111f, rates, 0.0000001f);
    }
    @Test
    public void whenFloatType_thenDoubleTypeSuccess(){
        float gradeAverage =2.05f;
        double average = gradeAverage;
        System.out.println(average); //2.049999952316284
        Assert.assertEquals(2.05, average, 0.01);

        Float monthlyRates = 2.1111111f;
        Double rates = monthlyRates.doubleValue();
        System.out.println(rates); //2.1111111640930176
        Assert.assertEquals(2.11111111, rates, 0.0000001);//true
    }

}
