package com.baeldung.testng;

import com.baeldung.testng.utils.Counter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeforeMethodAnnotationTest {
    private static final Logger log = LoggerFactory.getLogger(BeforeMethodAnnotationTest.class);

    Counter counter;

    @BeforeTest
    public void init() {
        log.info("Initializing ...");
        counter = new Counter(0);
    }

    @BeforeMethod
    public void givenCounterInitialized_whenResetTotalCount_thenTotalCountValueReset() {
        log.info("resetting total counter value ...");
        counter.resetTotalCount();
    }

    @Test
    public void givenCounterInitialized_whenAddingValue_thenTotalCountIncreased() {
        log.info("total counter before added: {}", counter.getTotalCount());
        counter.addCounter(2);
        log.info("total counter after added: {}", counter.getTotalCount());
    }

    @Test
    public void givenCounterInitialized_whenSubtractingValue_thenTotalCountDecreased() {
        log.info("total counter before subtracted: {}", counter.getTotalCount());
        counter.subtractCounter(2);
        log.info("total counter after subtracted: {}", counter.getTotalCount());
    }
}
