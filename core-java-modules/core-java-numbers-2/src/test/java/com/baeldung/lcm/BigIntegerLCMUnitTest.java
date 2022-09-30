package com.baeldung.lcm;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class BigIntegerLCMUnitTest {

    @Test
    public void testLCM() {
        BigInteger number1 = new BigInteger("12");
        BigInteger number2 = new BigInteger("18");
        BigInteger expectedLCM = new BigInteger("36");
        Assert.assertEquals(expectedLCM, BigIntegerLCM.lcm(number1, number2));
    }
}
