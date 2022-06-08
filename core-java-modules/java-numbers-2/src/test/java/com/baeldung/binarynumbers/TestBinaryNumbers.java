package com.baeldung.binarynumbers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestBinaryNumbers {
    private BinaryNumbers binaryNumbers = new BinaryNumbers();

    @Test
    // Paritition test for converting decimal to binary 
    public void testConvertDecimalToBinary() {
        Assert.assertEquals(100, BinaryNumbers.convertDecimalToBinary(4));
        Assert.assertEquals(1000, BinaryNumbers.convertDecimalToBinary(8));
    }

    @Test
    // Paritition test for converting binary to decimal 
    public void testConvertBinaryToDecimal() {
        Assert.assertEquals(4, BinaryNumbers.convertBinaryToDecimal(100));
        Assert.assertEquals(8, BinaryNumbers.convertBinaryToDecimal(1000));
    }

    @Test
    // Paritition test for adding binary number
    public void testAddBinaryNumber() {
        Assert.assertEquals(1100, BinaryNumbers.addBinaryNumber(100, 1000));
    }

    @Test
    // Paritition test for substracting binary number
    public void testSubstractBinaryNumber() {
        Assert.assertEquals(900, BinaryNumbers.substractBinaryNumber(1000, 100));
    }
}