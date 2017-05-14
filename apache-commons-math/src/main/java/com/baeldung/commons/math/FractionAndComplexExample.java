package com.baeldung.commons.math;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.fraction.FractionFormat;

public class FractionAndComplexExample {

    public void run() {
        Fraction lhs = new Fraction(1, 3);
        Fraction rhs = new Fraction(2, 5);
        Fraction sum = lhs.add(rhs);

        System.out.println(new FractionFormat().format(sum));

        Complex first  = new Complex(1.0, 3.0);
        Complex second = new Complex(2.0, 5.0);

        Complex power = first.pow(second);

        System.out.println(power);
    }

}
