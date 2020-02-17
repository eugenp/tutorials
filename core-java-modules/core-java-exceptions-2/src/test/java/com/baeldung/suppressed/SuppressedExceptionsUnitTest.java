package com.baeldung.suppressed;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SuppressedExceptionsUnitTest {

    @Test(expected = NullPointerException.class)
    public void givenNonExistentFileName_whenAttemptFileOpen_thenNullPointerException() throws IOException {
        SuppressedExceptionsDemo.demoSuppressedException("/non-existent-path/non-existent-file.txt");
    }
    
    @Test
    public void givenNonExistentFileName_whenAttemptFileOpenStoreSuppressed_thenSuppressedExceptionAvailable() {
        try {
            SuppressedExceptionsDemo.demoAddSuppressedException("/non-existent-path/non-existent-file.txt");
        } catch (Exception e) {
            assertEquals(1, e.getSuppressed().length);
            assertThat(e.getSuppressed()[0], instanceOf(FileNotFoundException.class));
        }
    }
    
    @Test
    public void whenUsingExceptionalResource_thenSuppressedExceptionAvailable() {
        try {
            SuppressedExceptionsDemo.demoExceptionalResource();
        } catch (Exception e) {
            assertEquals("Thrown from processSomething()", e.getMessage());
            assertEquals(1, e.getSuppressed().length);
            assertEquals("Thrown from close()", e.getSuppressed()[0].getMessage());
        }
    }
}
