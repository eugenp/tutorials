package com.baeldung.jdiffered;

import org.junit.Test;

import com.baeldung.jdiffered.PipeDemo.Result;

import static org.junit.Assert.assertEquals;

public class AppTest {

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
