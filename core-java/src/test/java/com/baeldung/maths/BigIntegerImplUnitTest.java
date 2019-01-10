package com.baeldung.maths;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class BigIntegerImplUnitTest {

    @Test
    public void givenBigIntegerNumbers_whenAddedTogether_thenGetExpectedResult() {
        BigInteger numStarsMilkyWay = new BigInteger("8731409320171337804361260816606476");
        BigInteger numStarsAndromeda = new BigInteger("5379309320171337804361260816606476");
        System.out.println("length:{} , numStarsMilkyWay:{}" + numStarsMilkyWay.toString().length() +" , " + numStarsMilkyWay);
        System.out.println("length:{} , numStarsAndromeda:{}" + numStarsAndromeda.toString().length() +" , " + numStarsAndromeda);

        BigInteger totalStars = numStarsMilkyWay.add(numStarsAndromeda);
        System.out.println("length:{} , totalStars:{}" + totalStars.toString().length() +" , " + totalStars);
        BigInteger result = new BigInteger("14110718640342675608722521633212952");

        Assert.assertEquals(result, totalStars);
    }

}
