package com.baeldung.vavr.exception.handling;

import static com.baeldung.vavr.exception.handling.VavrExceptionHandling.readFromFile;
import static com.baeldung.vavr.exception.handling.VavrExceptionHandling.usingFunctionLifting;
import static com.baeldung.vavr.exception.handling.VavrExceptionHandling.usingTryContainer;
import static com.baeldung.vavr.exception.handling.VavrExceptionHandling.validateBookingDates;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vavr.API;
import io.vavr.CheckedFunction1;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Try;
import io.vavr.control.Validation;

public class VavrExceptionHandlingUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(VavrExceptionHandlingUnitTest.class);
    
    private List<Integer> integers;

    @Before
    public void init() {
        integers = Arrays.asList(3, 9, 7, 0, 10, 20);
    }

    @Test(expected = IOException.class)
    public void exceptionCausingMethod_UsingCheckedFunction_ThenSuccess() {
    	CheckedFunction1<Integer, Integer> fn = i -> readFromFile(i);
    	    	    
    	integers.stream().map(fn.unchecked()).forEach(i -> LOG.debug("{}", i));
    }
    
    @Test(expected = IOException.class)
    public void exceptionCausingMethod_UsingAPI_ThenSuccess() {
    	integers.stream().map(API.unchecked(i -> readFromFile(i))).forEach(i -> LOG.debug("{}", i));
    }
    
    @Test
    public void exceptionCausingStatement_UsingLift_ThenFailure() {
    	Option<Integer> value = usingFunctionLifting("abc");

    	Assert.assertSame(-1, value.getOrElse(-1));
    }

    @Test
    public void exceptionCausingStatement_UsingLift_ThenSuccess() {
    	Option<Integer> value = usingFunctionLifting("5");

    	Assert.assertSame(5, value.getOrElse(-1));
    }

    @Test
    public void exceptionCausingMethod_UsingTry_ThenSuccess() {
    	Try<Integer> successResult = usingTryContainer(5);

    	Assert.assertSame(25, successResult.get());
    }

    @Test(expected = IOException.class)
    public void exceptionCausingMethod_UsingTry_ThenFailure() {
    	Try<Integer> failureResult = usingTryContainer(0);

    	Assert.assertTrue(failureResult.getCause() instanceof IOException);
    	failureResult.get(); // Re-throws exception
    }
    
    @Test(expected = NoSuchElementException.class)
    public void givenInvalidDates_UsingValidationAPI_ThenFailure() {
    	Validation<Seq<Throwable>, Boolean> result = validateBookingDates("13/11/2017", "16/11/2017");
    	
    	Assert.assertTrue(result.isInvalid());
    	Assert.assertTrue(result.getError().get(0) instanceof DateTimeParseException);
    	Assert.assertTrue(result.getError().get(1) instanceof DateTimeParseException);
    	result.get();  // throws exception when invoked on an invalid
    }
    
    @Test
    public void givenValidDates_UsingValidationAPI_ThenSuccess() {
    	Validation<Seq<Throwable>, Boolean> result = validateBookingDates("11/13/2017", "11/16/2017");

    	Assert.assertTrue(result.isValid());
    	Assert.assertTrue(result.get());
    }
    
}
