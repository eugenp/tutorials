package com.baeldung.getTestName;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class JUnit4SimpleTestNameUnitTest {

    @Rule
    public TestName name = new TestName();

    @Test
    public void testMethod() {
        System.out.println("displayName = " + name.getMethodName());
    }
}
