package com.baeldung.java8.lambda.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class LambdaExceptionWrappers {

    private static final Logger LOGGER = LoggerFactory.getLogger(LambdaExceptionWrappers.class);

    public static Consumer<Integer> lambdaWrapper(Consumer<Integer> consumer) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (ArithmeticException e) {
                LOGGER.error("Arithmetic Exception occured : {}", e.getMessage());
            }
        };
    }

    static <T, E extends Exception> Consumer<T> consumerWrapper(Consumer<T> consumer, Class<E> clazz) {
        return i -> {
            try {
                consumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = clazz.cast(ex);
                    LOGGER.error("Exception occured : {}", exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw ex;
                }
            }
        };
    }

    public static <T> Consumer<T> throwingConsumerWrapper(ThrowingConsumer<T, Exception> throwingConsumer) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {
        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    LOGGER.error("Exception occured : {}", exCast.getMessage());
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }

}