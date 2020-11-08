package com.baeldung.algorithms.largestpowerof2;

import org.nd4j.linalg.io.Assert;

public class LargestPowerOf2 {
    public long findLargestPowerOf2LessThanTheGivenNumber(long input) {
        Assert.isTrue(input > 1, "Invalid input");

        long firstPowerOf2 = 1;
        long nextPowerOf2 = 2;

        while (nextPowerOf2 < input) {
            firstPowerOf2 = nextPowerOf2;
            nextPowerOf2 = nextPowerOf2 * 2;
        }
        return firstPowerOf2;
    }

    public long findLargestPowerOf2LessThanTheGivenNumberUsingLogBase2(long input) {
        Assert.isTrue(input > 1, "Invalid input");

        long temp = input;
        if (input % 2 == 0) {
            temp = input - 1;
        }

        // Find log base 2 of a given number
        long power = (long) (Math.log(temp) / Math.log(2));
        long result = (long) Math.pow(2, power);

        return result;
    }

    public long findLargestPowerOf2LessThanTheGivenNumberUsingBitwiseAnd(long input) {
        Assert.isTrue(input > 1, "Invalid input");
        long result = 1;
        for (long i = input - 1; i > 1; i--) {
            if ((i & (i - 1)) == 0) {
                result = i;
                break;
            }
        }
        return result;
    }

    public long findLargestPowerOf2LessThanTheGivenNumberUsingBitShiftApproach(long input) {
        Assert.isTrue(input > 1, "Invalid input");
        long result = 1;
        long powerOf2;

        for (long i = 0; i < Long.BYTES * 8; i++) {
            powerOf2 = 1 << i;
            if (powerOf2 >= input) {
                break;
            }
            result = powerOf2;
        }
        return result;
    }
}
