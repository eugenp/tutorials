package com.baeldung.print2DArrays;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayPrinterTest {

    @Test
    public void testPrint2DNested() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        String expectedOutput = "1 2 3 \n4 5 6 \n7 8 9 \n";
        assertEquals(expectedOutput, ArrayPrinter.print2DNested(myArray));
    }

    @Test
    public void testPrint2DStream() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        String expectedOutput = "1 2 3 4 5 6 7 8 9 ";
        assertEquals(expectedOutput, ArrayPrinter.print2DStream(myArray));
    }

    @Test
    public void testPrint2DDeepToString() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        String expectedOutput = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]\n";
        assertEquals(expectedOutput, ArrayPrinter.print2DDeepToString(myArray));
    }

    @Test
    public void testPrintArrayString() {
        int[][] myArray = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        String expectedOutput = "[1, 2, 3]\n[4, 5, 6]\n[7, 8, 9]\n";
        assertEquals(expectedOutput, ArrayPrinter.printArrayString(myArray));
    }
}
