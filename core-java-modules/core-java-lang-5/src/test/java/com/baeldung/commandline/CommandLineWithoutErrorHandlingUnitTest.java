package com.baeldung.commandline;

import org.junit.Test;
import static org.junit.Assert.fail;

public class CommandLineWithoutErrorHandlingUnitTest {

    @Test(expected = NullPointerException.class)
    public void givenNullCommandLineArgument_whenPassedToMainFunction_thenExpectNullPointerException() {
        CommandLineWithoutErrorHandling.main(null);
    }
}