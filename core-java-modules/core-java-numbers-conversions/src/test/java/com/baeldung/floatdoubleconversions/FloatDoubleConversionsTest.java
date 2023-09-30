package com.baeldung.floatdoubleconversions;

import org.junit.Assert;
import org.junit.Test;

public class FloatDoubleConversionsTest {

    @Test
    public void whenDoubleType_thenFloatTypeSuccess(){
        double interestRatesYearly = 13.333333333333334;
        float interest = (float) interestRatesYearly;
        System.out.println(interest);  //13.333333
        Assert.assertTrue(Float.class.isInstance(interest));//true

        Double monthlyRates = 2.111111111111112;
        float rates = monthlyRates.floatValue();
        System.out.println(rates);  //2.1111112
        Assert.assertTrue(Float.class.isInstance(rates));//true
    }
    @Test
    public void whenFloatType_thenDoubleTypeSuccess(){
        float gradeAverage =2.05f;
        double average = gradeAverage;
        System.out.println(average);  //2.049999952316284
        Assert.assertTrue(Double.class.isInstance(average));//true

        Float monthlyRates = 2.1111112f;
        Double rates = monthlyRates.doubleValue();
        System.out.println(rates);  //2.1111112
        Assert.assertTrue(Double.class.isInstance(rates));//true
    }

}
