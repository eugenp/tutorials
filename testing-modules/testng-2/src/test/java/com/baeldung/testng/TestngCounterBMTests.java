package com.baeldung.testng;

import com.baeldung.testng.service.utils.Counter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@SpringBootTest
public class TestngCounterBMTests extends AbstractTestNGSpringContextTests {
    Counter counter;

    @BeforeTest
    public void init() {
        System.out.println("Initializing ...");
        counter = new Counter(0);
    }

    @BeforeMethod
    public void resetCounter() {
        System.out.println("resetting total counter value ...");
        counter.resetTotalCount();
    }

    @Test
    public void testAddCounter() {
        System.out.println("total counter before added: " + counter.getTotalCount());
        counter.addCounter(2);
        System.out.println("total counter after added: " + counter.getTotalCount());
    }

    @Test
    public void testSubtractCounter() {
        System.out.println("total counter before subtracted: " + counter.getTotalCount());
        counter.subtractCounter(2);
        System.out.println("total counter after subtracted: " + counter.getTotalCount());
    }
}
