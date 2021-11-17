package com.baeldung.getTestName;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnit5SimpleTestNameUnitTest {

    private boolean oddCheck(int number) {
        return (number % 2 != 0);
    }

    @Test
    public void givenNumbers_whenOddCheck_thenVerify(TestInfo testInfo) {
        System.out.println("displayName = " + testInfo.getDisplayName());
        int number = 5;
        assertTrue(oddCheck(number));
    }
}
