package com.baeldung.printstack;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.baeldung.PrintStack;

class PrintStackUnitTest {

    @Test
    void givenStack_whenUsingToString_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingToString_thenPrintStack());
        assertEquals("[10, 20, 30]", output);
    }

    @Test
    void givenStack_whenUsingForEach_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingForEach_thenPrintStack());
        assertEquals("10 20 30 ", output);
    }

    @Test
    void givenStack_whenUsingDirectForEach_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingDirectForEach_thenPrintStack());
        assertEquals("10\n20\n30\n", output.replace("\r\n", "\n"));
    }

    @Test
    void givenStack_whenUsingStreamReverse_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingStreamReverse_thenPrintStack());
        assertEquals("30\n20\n10\n", output.replace("\r\n", "\n"));
    }

    @Test
    void givenStack_whenUsingIterator_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingIterator_thenPrintStack());
        assertEquals("10 20 30 ", output);
    }

    @Test
    void givenStack_whenUsingListIteratorReverseOrder_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingListIteratorReverseOrder_thenPrintStack());
        assertEquals("30 20 10 ", output);
    }

    @Test
    void givenStack_whenUsingDeque_thenPrintStack() throws Exception {
        String output = tapSystemOut(() -> PrintStack.givenStack_whenUsingDeque_thenPrintStack());
        assertEquals("30 20 10 ", output);
    }
}
