package com.baeldung.floatdoubleconversions;

import org.junit.Test;

public class FloatDoubleConversionsTest {
    @Test
    public void givenFloatSamples(){
        float vatRate = 14.432511f;
        System.out.println("vatRate:"+vatRate);
        Float localTaxRate = 20.12434f;
        System.out.println("localTaxRate:"+localTaxRate);
    }
    @Test
    public void givenDoubleSamples(){
        double shootingAverage = 56.00000000000001;
        System.out.println("shootingAverage:"+shootingAverage);
        Double assistAverage = 81.123000000045;
        System.out.println("assistAverage:"+assistAverage);
    }
    @Test
    public void givenDouble_convertToFloat(){
        double interestRatesYearly = 13.333333333333334;
        float interest = (float) interestRatesYearly;
        System.out.println(interest);  //13.333333

        Double monthlyRates = 2.111111111111112;
        float rates = monthlyRates.floatValue();
        System.out.println(rates);  //2.1111112
    }
    @Test
    public void givenFloat_convertToDouble(){
        float gradeAverage =2.05f;
        double average = gradeAverage;
        System.out.println(average);  //2.049999952316284

        Float monthlyRates = 2.1111112f;
        Double rates = monthlyRates.doubleValue();
        System.out.println(rates);  //2.1111112
    }

}
