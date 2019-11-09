package com.baeldung.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;

public class RepeatedTestAnnotationUnitTest {

    @BeforeEach
    void beforeEachTest() {
        System.out.println("Before Each Test");
    }

    @AfterEach
    void afterEachTest() {
        System.out.println("After Each Test");
        System.out.println("=====================");
    }

    @RepeatedTest(3)
    void repeatedTest(TestInfo testInfo) {
        System.out.println("Executing repeated test");
        assertEquals(2, Math.addExact(1, 1), "1 + 1 should equal 2");
    }

    @RepeatedTest(value = 3, name = RepeatedTest.LONG_DISPLAY_NAME)
    void repeatedTestWithLongName() {
        System.out.println("Executing repeated test with long name");
        assertEquals(2, Math.addExact(1, 1), "1 + 1 should equal 2");
    }

    @RepeatedTest(value = 3, name = RepeatedTest.SHORT_DISPLAY_NAME)
    void repeatedTestWithShortName() {
        System.out.println("Executing repeated test with long name");
        assertEquals(2, Math.addExact(1, 1), "1 + 1 should equal 2");
    }

    @RepeatedTest(value = 3, name = "Custom name {currentRepetition}/{totalRepetitions}")
    void repeatedTestWithCustomDisplayName() {
        assertEquals(2, Math.addExact(1, 1), "1 + 1 should equal 2");
    }

    @RepeatedTest(3)
    void repeatedTestWithRepetitionInfo(RepetitionInfo repetitionInfo) {
        System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition());
        assertEquals(3, repetitionInfo.getTotalRepetitions());
    }
}
