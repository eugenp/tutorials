package com.baeldung.algorithms.percentage;

import org.hamcrest.number.BigDecimalCloseTo;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalPercentageUnitTest {
    private BigDecimalPercentages pc = new BigDecimalPercentages();


    @Test
    public void shouldConvertToPercentageOfTotal(){
        BigDecimalCloseTo expected = new BigDecimalCloseTo(new BigDecimal(5.05), new BigDecimal(0.001));
        Assert.assertTrue("Result not as expected",expected.matchesSafely(
                pc.toPercentageOf(new BigDecimal(50.5),new BigDecimal(1000))));
    }

    @Test
    public void shouldCalculatePercentageOfTotal(){
        BigDecimalCloseTo expected = new BigDecimalCloseTo(new BigDecimal(31.40), new BigDecimal(0.001));
        Assert.assertTrue("Result not as expected",expected.matchesSafely(
                pc.percentOf(new BigDecimal(3.14),new BigDecimal(1000))));
    }

}
