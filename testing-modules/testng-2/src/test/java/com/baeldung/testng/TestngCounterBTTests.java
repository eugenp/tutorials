package com.baeldung.testng;

import com.baeldung.testng.service.utils.Counter;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

@SpringBootTest
public class TestngCounterBTTests extends AbstractTestNGSpringContextTests {
    Counter counter;

    @BeforeTest
    public void init() {
        System.out.println("Initializing ...");
        counter = new Counter(0);
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
        counter.subtractCounter(1);
        System.out.println("total counter after subtracted: " + counter.getTotalCount());
    }
}
