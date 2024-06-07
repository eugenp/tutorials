package com.baeldung.exception.classcastexception;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class BigIntegerConverterUnitTest {

    @Test
    public void givenBigIntegerWithinIntRange_thenShouldConvertToInt() {
        Object obj = new BigInteger("123456789");
        assertEquals(123456789, BigIntegerConverter.convertIntegerToInt(obj));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenBigIntegerOutsideIntRange_thenShouldThrowIllegalArgumentException() {
        Object obj = new BigInteger("12345678901234567890");
        BigIntegerConverter.convertIntegerToInt(obj);
    }

    @Test
    public void givenIntegerObject_thenShouldReturnIntegerValue() {
        Object obj = 123;
        assertEquals(123, BigIntegerConverter.convertIntegerToInt(obj));
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenUnsupportedType_thenShouldThrowIllegalArgumentException() {
        Object obj = "test";
        BigIntegerConverter.convertIntegerToInt(obj);
    }
}
