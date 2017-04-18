package com.baeldung.commons.math;

import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

public class ComplexTest {

    @Test
    public void testComplex() {
        Complex first  = new Complex(1.0, 3.0);
        Complex second = new Complex(2.0, 5.0);

        Complex power = first.pow(second);

        System.out.println(power);
    }

}
