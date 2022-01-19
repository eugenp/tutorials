package com.baeldung.gettestname;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Junit5ParameterizedTestNameUnitTest {

    private TestInfo testInfo;

    @BeforeEach
    void init(TestInfo testInfo) {
        this.testInfo = testInfo;
    }

    private boolean oddCheck(int number) {
        return (number % 2 != 0);
    }

    @ParameterizedTest(name = "givenNumbers_whenOddCheck_thenVerify{0}")
    @ValueSource(ints = { 1, 3, 5, -3, 15 })
    void givenNumbers_whenOddCheck_thenVerify(int number) {
        System.out.println("displayName = " + testInfo.getDisplayName());
        assertTrue(oddCheck(number));
    }
}
