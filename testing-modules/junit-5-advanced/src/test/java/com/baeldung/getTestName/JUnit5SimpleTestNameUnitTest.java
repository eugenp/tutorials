package com.baeldung.getTestName;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

public class JUnit5SimpleTestNameUnitTest {

    @Test
    @DisplayName("testMethod")
    public void test(TestInfo testInfo) {
        System.out.println("displayName = " + testInfo.getDisplayName());
    }
}
