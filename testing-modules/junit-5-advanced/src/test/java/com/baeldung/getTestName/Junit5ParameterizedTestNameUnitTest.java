package com.baeldung.getTestName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Junit5ParameterizedTestNameUnitTest {

    @BeforeEach
    void init(TestInfo testInfo) {
        String displayName = testInfo.getDisplayName();
        System.out.println("displayName = " + displayName);
    }

    @ParameterizedTest(name = "givenNumbers_whenOddCheck_thenVerify{0}")
    @ValueSource(ints = { 1, 3, 5, -3, 15 })
    public void givenNumbers_whenOddCheck_thenVerify(int number) {
        assertTrue(number % 2 != 0);
    }
}
