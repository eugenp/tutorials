package com.baeldung.binarynumbers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBinaryNumbers {
    private BinaryNumbers binaryNumbers = new BinaryNumbers();

    @Test
    // Paritition test for converting decimal to binary 
    public void testConvertDecimalToBinary() {
        assertEquals(Integer.valueOf(100), binaryNumbers.convertDecimalToBinary(4));
        assertEquals(Integer.valueOf(1000), binaryNumbers.convertDecimalToBinary(8));
    }

    @Test
    // Paritition test for converting binary to decimal 
    public void testConvertBinaryToDecimal() {
        assertEquals(Integer.valueOf(4), binaryNumbers.convertBinaryToDecimal(100));
        assertEquals(Integer.valueOf(8), binaryNumbers.convertBinaryToDecimal(1000));
    }

    @Test
    // Paritition test for adding binary number
    public void testAddBinaryNumber() {
        assertEquals(Integer.valueOf(1100), binaryNumbers.addBinaryNumber(100, 1000));
    }

    @Test
    // Paritition test for substracting binary number
    public void testSubstractBinaryNumber() {
        assertEquals(Integer.valueOf(100), binaryNumbers.substractBinaryNumber(1000, 100));
    }
}