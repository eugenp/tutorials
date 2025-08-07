package com.java.baeldung.spring.test;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Test7IT extends AbstractIntegrationTest {

    @Test
    public void test() {
        System.out.println("Test7IT.test");
    }

    @Nested
    public class NestedTest {

        @Test
        public void nested() {
            System.out.println("Test7IT.NestedTest.test");
        }
    }
}
