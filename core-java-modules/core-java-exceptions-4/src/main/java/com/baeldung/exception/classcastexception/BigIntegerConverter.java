package com.baeldung.exception.classcastexception;

import java.math.BigInteger;

public class BigIntegerConverter {
    public static int convertIntegerToInt(Object obj) {
        if (obj instanceof BigInteger) {
            BigInteger bigInt = (BigInteger) obj;
            if (bigInt.bitLength() <= 31) {
                return bigInt.intValue();
            } else {
                throw new IllegalArgumentException("BigInteger value is too large to fit in an int");
            }
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        } else {
            throw new IllegalArgumentException("Unsupported type: " + obj.getClass().getName());
        }
    }
}