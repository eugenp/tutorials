package com.baeldung.getTestName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Junit5ParameterizedTestNameUnitTest {

    @BeforeEach
    void init(TestInfo testInfo) {
        System.out.println("displayName = " + testInfo.getDisplayName());
    }

    private boolean oddCheck(int number) {
        return (number % 2 != 0);
    }

    @ParameterizedTest(name = "givenNumbers_whenOddCheck_thenVerify{0}")
    @ValueSource(ints = { 1, 3, 5, -3, 15 })
    public void givenNumbers_whenOddCheck_thenVerify(int number) {
        assertTrue(oddCheck(number));
    }
}
