package com.baeldung.lcm;

import org.junit.Assert;
import org.junit.Test;

public class EuclideanAlgorithmUnitTest {

    @Test
    public void testGCD() {
        Assert.assertEquals(6, EuclideanAlgorithm.gcd(12, 18));
    }

    @Test
    public void testLCM() {
        Assert.assertEquals(36, EuclideanAlgorithm.lcm(12, 18));
    }

    @Test
    public void testLCMForArray() {
        Assert.assertEquals(15, EuclideanAlgorithm.lcmForArray(new int[]{3, 5, 15}));
    }

    @Test
    public void testLCMByLambdaForArray() {
        Assert.assertEquals(15, EuclideanAlgorithm.lcmByLambda(new int[]{3, 5, 15}));
    }
}
