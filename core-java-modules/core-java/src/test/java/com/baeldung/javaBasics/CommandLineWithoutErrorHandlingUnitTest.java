package com.baeldung.javaBasics;

import org.junit.Test;
import static org.junit.Assert.fail;

public class CommandLineWithoutErrorHandlingUnitTest {

    @Test
    public void testMainMethodWithNullArguments() {
        try {
            CommandLineWithoutErrorHandling.main(null);
            fail("Expected a NullPointerException to be thrown");
        } catch (NullPointerException npe) {
            // The main method correctly threw a NullPointerException, so the test passes
        }
    }
}