package com.baeldung.commons.math;

import org.apache.commons.math3.complex.Complex;
import org.junit.Assert;
import org.junit.Test;

public class ComplexUnitTest {

    @Test
    public void whenComplexPow_thenCorrect() {
        Complex first = new Complex(1.0, 3.0);
        Complex second = new Complex(2.0, 5.0);

        Complex power = first.pow(second);

        Assert.assertEquals(-0.007563724861696302, power.getReal(), 1e-7);
        Assert.assertEquals(0.01786136835085382, power.getImaginary(), 1e-7);
    }

}
