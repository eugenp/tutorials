package com.baeldung.before_all_global;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ExampleTest {

    @BeforeAll
    static void setup() {
        System.out.println("Execute: BeforeAll");
        // Initialize class-specific resources
    }

    @Test
    void test1() {
        System.out.println("Execute test 1");
        // Test logic
    }

    @Test
    void test2() {
        System.out.println("Execute test 2");
        // Test logic
    }
}
