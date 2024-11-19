package com.baeldung.testng;

import com.baeldung.testng.service.utils.Counter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class BeforeTestUnitTest extends AbstractTestNGSpringContextTests {
    private static final Logger log = LoggerFactory.getLogger(BeforeTestUnitTest.class);

    Counter counter;

    @BeforeTest
    public void counterInitialized() {
        log.info("Initializing ...");
        counter = new Counter(0);
    }

    @Test
    public void counterInitializedAddingValueTotalCountIncreased() {
        log.info("total counter before added: " + counter.getTotalCount());
        counter.addCounter(2);
        log.info("total counter after added: " + counter.getTotalCount());
    }

    @Test
    public void counterInitializedSubtractingValueTotalCountDecreased() {
        log.info("total counter before subtracted: " + counter.getTotalCount());
        counter.subtractCounter(1);
        log.info("total counter after subtracted: " + counter.getTotalCount());
    }
}
