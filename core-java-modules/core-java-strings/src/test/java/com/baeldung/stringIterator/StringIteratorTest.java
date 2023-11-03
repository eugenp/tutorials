package com.baeldung.stringIterator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringIteratorTest {
  
    @Test
    public void whenUseCharArrayMethod_thenIterate() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaCharArray(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void whenUseJavaForLoop_thenIterate() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaForLoop(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void whenUseForEachMethod_thenIterate() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.java8ForEach(input);
        assertEquals(expectedOutput, result);
    }
  
    @Test
    public void whenUseCharacterIterator_thenIterate() {
        String input = "Hello, Baeldung!";
        String expectedOutput = "Hello, Baeldung!";
        String result = StringIterator.javaCharacterIterator(input);
        assertEquals(expectedOutput, result);
    }
}
