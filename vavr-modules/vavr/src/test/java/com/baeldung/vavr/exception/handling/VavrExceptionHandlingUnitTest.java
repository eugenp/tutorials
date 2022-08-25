package com.baeldung.vavr.exception.handling;

import io.vavr.API;
import io.vavr.CheckedFunction1;
import io.vavr.Value;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VavrExceptionHandlingUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(VavrExceptionHandlingUnitTest.class);

    private List<Integer> integers;
    private List<Integer> validIntegersOnly;

    @Before
    public void init() {
        integers = Arrays.asList(3, 9, 7, 0, 10, 20);
        validIntegersOnly = Arrays.asList(3, 9, 7, 5, 10, 20);
    }

    @Test
    public void exceptionCausingMethod_UsingCheckedFunction_ThenSuccess() {
        CheckedFunction1<Integer, Integer> fn = i -> readFromFile(i);

        validIntegersOnly.stream().map(fn.unchecked()).forEach(i -> LOG.debug("{}", i));
    }

    @Test(expected = IOException.class)
    public void exceptionCausingMethod_UsingCheckedFunction_ThenFailure() {
        CheckedFunction1<Integer, Integer> fn = i -> readFromFile(i);

        integers.stream().map(fn.unchecked()).forEach(i -> LOG.debug("{}", i));
    }

    @Test
    public void exceptionCausingMethod_UsingAPI_ThenSuccess() {
        validIntegersOnly.stream().map(API.unchecked(i -> readFromFile(i))).forEach(i -> LOG.debug("{}", i));
    }

    @Test(expected = IOException.class)
    public void exceptionCausingMethod_UsingAPI_ThenFailure() {
        integers.stream().map(API.unchecked(i -> readFromFile(i))).forEach(i -> LOG.debug("{}", i));
    }

    @Test
    public void exceptionCausingMethod_UsingLift_ThenSuccess() {
        validIntegersOnly.stream().map(CheckedFunction1.lift(i -> readFromFile(i)))
          .map(i -> i.getOrElse(-1))
          .forEach(i -> {
              Assert.assertNotSame(-1, i);
              LOG.debug("{}", i);
          });
    }

    @Test
    public void exceptionCausingMethod_UsingLift_ThenFailure() {
        integers.stream()
          .map(CheckedFunction1.lift(i -> readFromFile(i)))
          .map(i -> i.getOrElse(-1))
          .forEach(i -> LOG.debug("{}", i));

    }

    @Test
    public void exceptionCausingMethod_UsingTry_ThenSuccess() {
        integers.stream()
          .map(CheckedFunction1.liftTry(VavrExceptionHandlingUnitTest::readFromFile))
          .flatMap(Value::toJavaStream)
          .forEach(i -> LOG.debug("{}", i));
    }

    private static Integer readFromFile(Integer i) throws IOException {
        if (i == 0) {
            throw new IOException(); // mock IOException
        }
        return i * i;
    }

}
