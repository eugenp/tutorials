package com.baeldung.noexception;

import com.machinezoo.noexception.Exceptions;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoExceptionUnitTest {

    private static Logger logger = LoggerFactory.getLogger(NoExceptionUnitTest.class);

    @Test
    public void whenStdExceptionHandling_thenCatchAndLog() {
        try {
            System.out.println("Result is " + Integer.parseInt("foobar"));
        } catch (Throwable exception) {
            logger.error("Caught exception:", exception);
        }
    }

    @Test
    public void whenDefaultNoException_thenCatchAndLog() {
        Exceptions.log().run(() -> System.out.println("Result is " + Integer.parseInt("foobar")));
    }

    @Test
    public void givenLogger_whenDefaultNoException_thenCatchAndLogWithClassName() {
        Exceptions.log(logger).run(() -> System.out.println("Result is " + Integer.parseInt("foobar")));
    }

    @Test
    public void givenLoggerAndMessage_whenDefaultNoException_thenCatchAndLogWithMessage() {
        Exceptions.log(logger, "Something went wrong:").run(() -> System.out.println("Result is " + Integer.parseInt("foobar")));
    }

    @Test
    public void givenDefaultValue_whenDefaultNoException_thenCatchAndLogPrintDefault() {
        System.out.println("Result is " + Exceptions.log(logger, "Something went wrong:").get(() -> Integer.parseInt("foobar")).orElse(-1));
    }

    @Test(expected = Error.class)
    public void givenCustomHandler_whenError_thenRethrowError() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        customExceptionHandler.run(() -> throwError());
    }

    @Test
    public void givenCustomHandler_whenException_thenCatchAndLog() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        customExceptionHandler.run(() -> throwException());
    }

    private static void throwError() {
        throw new Error("This is very bad.");
    }

    private static void throwException() {
        String testString = "foo";
        testString.charAt(5);
    }

}
