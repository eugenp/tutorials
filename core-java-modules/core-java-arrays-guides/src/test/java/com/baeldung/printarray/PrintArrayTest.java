package com.baeldung.printarray;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.baeldung.printarrays.PrintArrayJava;

class PrintArrayTest {

    @Test
    void testPrintArrayUsingForLoop() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees Peter Asghar Joseph Alex";
        assertEquals(expected, printArray.printArrayUsingForLoop(empArray));
    }

    @Test
    void testPrintArrayUsingForEachLoop() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees\nPeter\nAsghar\nJoseph\nAlex";
        assertEquals(expected, printArray.printArrayUsingForEachLoop(empArray));
    }

    @Test
    void testPrintArrayUsingToString() {
        PrintArrayJava printArray = new PrintArrayJava();
        int[] empIDs = {10, 12, 13, 15, 17};
        String expected = "[10, 12, 13, 15, 17]";
        assertEquals(expected, printArray.printArrayUsingToString(empIDs));
    }

    @Test
    void testPrintArrayUsingAsList() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "[Anees, Peter, Asghar, Joseph, Alex]";
        assertEquals(expected, printArray.printArrayUsingAsList(empArray));
    }

    @Test
    void testPrintArrayUsingStreams() {
        PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees\nPeter\nAsghar\nJoseph\nAlex";
        assertEquals(expected, printArray.printArrayUsingStreams(empArray));
    }
    @Test
    void testPrintArrayUsingJoin() {
    	PrintArrayJava printArray = new PrintArrayJava();
        String[] empArray = {"Anees", "Peter", "Asghar", "Joseph", "Alex"};
        String expected = "Anees\nPeter\nAsghar\nJoseph\nAlex";
        assertEquals(expected, printArray.printArrayUsingJoin(empArray));
    }
}
