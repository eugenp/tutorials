package com.baeldung.before.all.global;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    @BeforeAll
    static void setup() {
        System.out.println("ExampleTest1 - Execute: BeforeAll");
        // Initialize class-specific resources
    }

    @Test
    void test1() {
        System.out.println("ExampleTest1 - Execute test 1");
        // Test logic
    }

    @Test
    void test2() {
        System.out.println("ExampleTest1 - Execute test 2");
        // Test logic
    }
}
