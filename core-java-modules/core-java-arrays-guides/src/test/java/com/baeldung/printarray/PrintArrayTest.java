package com.baeldung.printarray;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrintArrayTest {

    @Test
    public void testPrintArrayUsingForLoop() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees Peter Asghar Joseph Alex";
        assertEquals(expected, printArray.printArrayUsingForLoop(empArray));
    }

    @Test
    public void testPrintArrayUsingForEachLoop() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees\nPeter\nAsghar\nJoseph\nAlex";
        assertEquals(expected, printArray.printArrayUsingForEachLoop(empArray));
    }

    @Test
    public void testPrintArrayUsingToString() {
        PrintArrayJava printArray = new PrintArrayJava();
        int[] empIDs = {10, 12, 13, 15, 17};
        String expected = "[10, 12, 13, 15, 17]";
        assertEquals(expected, printArray.printArrayUsingToString(empIDs));
    }

    @Test
    public void testPrintArrayUsingAsList() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "[Anees, Peter, Asghar, Joseph, Alex]";
        assertEquals(expected, printArray.printArrayUsingAsList(empArray));
    }

    @Test
    public void testPrintArrayUsingStreams() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees\nPeter\nAsghar\nJoseph\nAlex";
        assertEquals(expected, printArray.printArrayUsingStreams(empArray));
    }

    @Test
    public void testPrint2DArrayUsingNestedLoops() {
        PrintArrayJava printArray = new PrintArrayJava();
        int[][] exampleArr = {{-1, 0, 1}, {10, 20, 30}, {5, 10, 15}};
        String expected = "-1 0 1 \n10 20 30 \n5 10 15";
        assertEquals(expected, printArray.print2DArrayUsingNestedLoops(exampleArr));
    }

    @Test
    public void testPrint2DArrayUsingDeepToString() {
        PrintArrayJava printArray = new PrintArrayJava();
        int[][] exampleArr = {{-1, 0, 1}, {10, 20, 30}, {5, 10, 15}};
        String expected = "[[-1, 0, 1], [10, 20, 30], [5, 10, 15]]";
        assertEquals(expected, printArray.print2DArrayUsingDeepToString(exampleArr));
    }
}
