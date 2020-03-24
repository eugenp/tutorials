package com.baeldung.migration.junit4;

import org.junit.Rule;
import org.junit.Test;

import com.baeldung.migration.junit4.rules.TraceUnitTestRule;

public class RuleExampleUnitTest {

    @Rule
    public final TraceUnitTestRule traceRuleTests = new TraceUnitTestRule();

    @Test
    public void whenTracingTests() {
        System.out.println("This is my test");
        /*...*/
    }
}
