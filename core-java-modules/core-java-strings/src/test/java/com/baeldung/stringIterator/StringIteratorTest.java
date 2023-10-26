package com.baeldung.stringIterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringIteratorTest {
  
    @Test
    public void testJavaCharArray() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaCharArray(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void testJavaForLoop() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaForLoop(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void testJava8ForEach() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.java8ForEach(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void testJavaRegExp() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaRegExp(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void testJavaCharacterIterator() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaCharacterIterator(input);
        assertEquals(expectedOutput, result);
    }
}
