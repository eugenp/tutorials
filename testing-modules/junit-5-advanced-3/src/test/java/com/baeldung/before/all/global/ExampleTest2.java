package com.baeldung.before.all.global;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ExampleTest2 {

    @BeforeAll
    static void setup() {
        System.out.println("ExampleTest2 - Execute: BeforeAll");
        // Initialize class-specific resources
    }

    @Test
    void test1() {
        System.out.println("ExampleTest2 - Execute test 1");
        // Test logic
    }
}
