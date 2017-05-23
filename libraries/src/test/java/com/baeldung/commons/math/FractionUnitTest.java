package com.baeldung.commons.math;

import org.apache.commons.math3.fraction.Fraction;
import org.junit.Assert;
import org.junit.Test;

public class FractionUnitTest {

    @Test
    public void whenFractionAdd_thenCorrect() {
        Fraction lhs = new Fraction(1, 3);
        Fraction rhs = new Fraction(2, 5);
        Fraction sum = lhs.add(rhs);

        Assert.assertEquals(11, sum.getNumerator());
        Assert.assertEquals(15, sum.getDenominator());
    }

}
