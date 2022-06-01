package com.baeldung;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Example1ManualTest {

    @Test
    public void test1a() {
        block(3000);
    }

    @Test
    public void test1b() {
        block(3000);
    }

    public static void block(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
}
