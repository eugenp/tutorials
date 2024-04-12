package com.baeldung.jdeffered;

import com.baeldung.jdeffered.PipeDemo.Result;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JDeferredUnitTest {

    @Test
    public void givenJob_expectPromise() {
        PromiseDemo.startJob("Baeldung Job");
    }

    @Test
    public void givenMsg_expectModifiedMsg() {
        String msg = FilterDemo.filter("Baeldung");
        assertEquals("Hello Baeldung", msg);
    }

    @Test
    public void givenNum_validateNum_expectStatus() {
        Result result = PipeDemo.validate(80);
        assertEquals(result, Result.SUCCESS);
    }
}
