package com.baeldung.commons.math;

import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.FractionFormat;
import org.junit.Test;

public class FractionTest {

    @Test
    public void testFraction() {
        Fraction lhs = new Fraction(1, 3);
        Fraction rhs = new Fraction(2, 5);
        Fraction sum = lhs.add(rhs);

        System.out.println(new FractionFormat().format(sum));
    }

}
