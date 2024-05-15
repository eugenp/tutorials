package com.baeldung.algorithms.largestpowerof2;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class LargestPowerOf2UnitTest {
    private long input;
    private long expectedResult;

    public LargestPowerOf2UnitTest(long input, long expectedResult) {
        this.input = input;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters(name = "{index}: verifyLargestPowerOf2LessThanTheGivenNumber({0}) = {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { 2, 1 }, { 4, 2 }, { 500, 256 }, { 512, 256 }, { 1050, 1024 } });
    }

    @Test
    public void givenValidInput_verifyLargestPowerOf2LessThanTheGivenNumber() {
        LargestPowerOf2 largestPowerOf2 = new LargestPowerOf2();

        long result = largestPowerOf2.findLargestPowerOf2LessThanTheGivenNumber(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void givenValidInput_verifyLargestPowerOf2LessThanTheGivenNumberUsingLogBase2() {
        LargestPowerOf2 largestPowerOf2 = new LargestPowerOf2();

        long result = largestPowerOf2.findLargestPowerOf2LessThanTheGivenNumberUsingLogBase2(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void givenValidInput_verifyLargestPowerOf2LessThanTheGivenNumberBitwiseAnd() {
        LargestPowerOf2 largestPowerOf2 = new LargestPowerOf2();

        long result = largestPowerOf2.findLargestPowerOf2LessThanTheGivenNumberUsingBitwiseAnd(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void givenValidInput_verifyLargestPowerOf2LessThanTheGivenNumberBitShiftApproach() {
        LargestPowerOf2 largestPowerOf2 = new LargestPowerOf2();

        long result = largestPowerOf2.findLargestPowerOf2LessThanTheGivenNumberUsingBitShiftApproach(input);

        Assert.assertEquals(expectedResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenInvalidInput_ShouldThrowException() {
        LargestPowerOf2 largestPowerOf2 = new LargestPowerOf2();
        largestPowerOf2.findLargestPowerOf2LessThanTheGivenNumber(1);
    }
}