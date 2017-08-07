package com.baeldung.noexception;

import com.machinezoo.noexception.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoExceptionHandlingLoggerMessage {

    private static Logger logger = LoggerFactory.getLogger(NoExceptionHandlingLoggerMessage.class);

    public static void main(String[] args) {
        System.out.println("Result is " + Exceptions.log(logger)
            .get(() -> +Integer.parseInt("foobar"))
            .orElse(-1));
    }
}
