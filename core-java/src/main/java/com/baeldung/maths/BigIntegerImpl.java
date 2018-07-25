package com.baeldung.maths;

import java.math.BigInteger;

public class BigIntegerImpl {

    public static void main(String[] args) {

        BigInteger numStarsMilkyWay = new BigInteger("8731409320171337804361260816606476");
        BigInteger numStarsAndromeda = new BigInteger("5379309320171337804361260816606476");

        BigInteger totalStars = numStarsMilkyWay.add(numStarsAndromeda);

    }

}
