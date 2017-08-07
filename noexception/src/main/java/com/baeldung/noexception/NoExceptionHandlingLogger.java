package com.baeldung.noexception;

import com.machinezoo.noexception.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoExceptionHandlingLogger {

    private static Logger logger = LoggerFactory.getLogger(NoExceptionHandlingLogger.class);

    public static void main(String[] args) {
        System.out.println("Result is " + Exceptions.log(logger, "Something went wrong:")
            .get(() -> +Integer.parseInt("foobar"))
            .orElse(-1));
    }
}
