package com.baeldung.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TraceUnitExtension.class)
public class RuleExampleUnitTest {

    @Test
    public void whenTracingTests() {
        System.out.println("This is my test");
        /*...*/
    }
}
