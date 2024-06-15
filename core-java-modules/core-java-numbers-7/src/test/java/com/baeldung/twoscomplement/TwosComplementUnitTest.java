package com.baeldung.twoscomplement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;

public class TwosComplementUnitTest {

    @ParameterizedTest(name = "Twos Complement of {0} with number of bits {1}")
    @CsvSource({
            "0, 4, 0000",
            "1, 4, 0001",
            "-1, 4, 1111",
            "7, 4, 0111",
            "-7, 4, 1001",
            "12345, 16, 0011 0000 0011 1001",
            "-12345, 16, 1100 1111 1100 0111"
    })
    public void givenNumberAndBits_getTwosComplement(String number, int noOfBits, String expected) {
        String twosComplement = TwosComplement.decimalToTwosComplementBinary(new BigInteger(number), noOfBits);
        Assertions.assertEquals(expected, twosComplement);
    }

    @ParameterizedTest(name = "Twos Complement of {0} with number of bits {1}")
    @CsvSource({
            "0, 4, 0000",
            "1, 4, 0001",
            "-1, 4, 1111",
            "7, 4, 0111",
            "-7, 4, 1001",
            "12345, 16, 0011 0000 0011 1001",
            "-12345, 16, 1100 1111 1100 0111"
    })
    public void givenNumberAndBits_getTwosComplementUsingShortcut(String number, int noOfBits, String expected) {
        String twosComplement = TwosComplement.decimalToTwosComplementBinaryUsingShortCut(new BigInteger(number), noOfBits);
        Assertions.assertEquals(expected, twosComplement);
    }

    @Test
    public void givenNumberOutsideBitsRange_throwException() {
        Exception ex = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            TwosComplement.decimalToTwosComplementBinary(BigInteger.valueOf(8), 3);
        });
        Assertions.assertEquals("3 bits is not enough to represent the number 8", ex.getMessage());
    }
}
