package com.baeldung.binarynumbers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BinaryNumbersUnitTest {

    private BinaryNumbers binaryNumbers = new BinaryNumbers();

    @Test
    public void given_decimalNumber_then_returnBinaryNumber() {
        assertEquals(Integer.valueOf(1000), binaryNumbers.convertDecimalToBinary(8));
        assertEquals(Integer.valueOf(10100), binaryNumbers.convertDecimalToBinary(20));
    }

    @Test
    public void given_decimalNumber_then_convertToBinaryNumber() {
        assertEquals("1000", Integer.toBinaryString(8));
        assertEquals("10100", Integer.toBinaryString(20));
    }

    @Test
    public void given_binaryNumber_then_ConvertToDecimalNumber() {
        assertEquals(8, Integer.parseInt("1000", 2));
        assertEquals(20, Integer.parseInt("10100", 2));
    }

    @Test
    public void given_binaryNumber_then_returnDecimalNumber() {
        assertEquals(Integer.valueOf(8), binaryNumbers.convertBinaryToDecimal(1000));
        assertEquals(Integer.valueOf(20), binaryNumbers.convertBinaryToDecimal(10100));
    }

    @Test
    public void given_twoBinaryNumber_then_returnAddition() {
        // adding 4 and 10
        assertEquals(Integer.valueOf(1110), binaryNumbers.addBinaryNumber(100, 1010));

        // adding 26 and 14
        assertEquals(Integer.valueOf(101000), binaryNumbers.addBinaryNumber(11010, 1110));
    }

    @Test
    public void given_twoBinaryNumber_then_returnSubtraction() {
        // subtracting 16 from 25
        assertEquals(Integer.valueOf(1001), binaryNumbers.substractBinaryNumber(11001, 10000));

        // subtracting 29 from 16, the output here is negative
        assertEquals(Integer.valueOf(1101), binaryNumbers.substractBinaryNumber(10000, 11101));
    }

    @Test
    public void given_binaryLiteral_thenReturnDecimalValue() {

        byte five = 0b101;
        assertEquals((byte) 5, five);

        short three = 0b11;
        assertEquals((short) 3, three);

        int nine = 0B1001;
        assertEquals(9, nine);

        long twentyNine = 0B11101;
        assertEquals(29, twentyNine);

        int minusThirtySeven = -0B100101;
        assertEquals(-37, minusThirtySeven);

    }

}
