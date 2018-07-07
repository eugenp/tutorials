package com.baeldung.java8.lambda.exceptions;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.baeldung.java8.lambda.exceptions.LambdaExceptionWrappers.*;

public class LambdaExceptionWrappersUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(LambdaExceptionWrappersUnitTest.class);

    private List<Integer> integers;

    @Before
    public void init() {
        integers = Arrays.asList(3, 9, 7, 0, 10, 20);
    }

    @Test
    public void whenNoExceptionFromLambdaWrapper_thenSuccess() {
        integers.forEach(lambdaWrapper(i -> LOG.debug("{}", 50 / i)));
    }

    @Test
    public void whenNoExceptionFromConsumerWrapper_thenSuccess() {
        integers.forEach(consumerWrapper(i -> LOG.debug("{}", 50 / i), ArithmeticException.class));
    }

    @Test(expected = RuntimeException.class)
    public void whenExceptionFromThrowingConsumerWrapper_thenSuccess() {
        integers.forEach(throwingConsumerWrapper(i -> writeToFile(i)));
    }

    @Test
    public void whenNoExceptionFromHandlingConsumerWrapper_thenSuccess() {
        integers.forEach(handlingConsumerWrapper(i -> writeToFile(i), IOException.class));
    }

    private void writeToFile(Integer i) throws IOException {
        if (i == 0) {
            throw new IOException(); // mock IOException
        }
    }
}
