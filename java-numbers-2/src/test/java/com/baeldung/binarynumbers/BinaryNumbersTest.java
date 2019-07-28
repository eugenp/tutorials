package com.baeldung.binarynumbers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.binarynumbers.BinaryNumbers;

public class BinaryNumbersTest {

    private BinaryNumbers binaryNumbers = new BinaryNumbers();

    @Test
    public void testConvertDecimalToBinary() {
        assertEquals(Integer.valueOf(1000), binaryNumbers.convertDecimalToBinary(8));
        assertEquals(Integer.valueOf(10100), binaryNumbers.convertDecimalToBinary(20));
    }

    @Test
    public void testConvertBinaryToDecimal() {
        assertEquals(Integer.valueOf(8), binaryNumbers.convertBinaryToDecimal(1000));
        assertEquals(Integer.valueOf(20), binaryNumbers.convertBinaryToDecimal(10100));
    }

    @Test
    public void testAddBinaryNumber() {
        // adding 4 and 10
        assertEquals(Integer.valueOf(1110), binaryNumbers.addBinaryNumber(100, 1010));

        // adding 26 and 14
        assertEquals(Integer.valueOf(101000), binaryNumbers.addBinaryNumber(11010, 1110));
    }

    @Test
    public void testSubstractBinaryNumber() {
        // subtracting 16 from 25
        assertEquals(Integer.valueOf(1001), binaryNumbers.substractBinaryNumber(11001, 10000));

        // subtracting 29 from 16, the output here is negative
        assertEquals(Integer.valueOf(1101), binaryNumbers.substractBinaryNumber(10000, 11101));
    }

}
