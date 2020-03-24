package com.stackify.test;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

public class IncrementTest {
    
    private static Logger logger = LogManager.getLogger(IncrementTest.class);

    @BeforeEach
    public void increment() {
        logger.info("Before Each Test");
    }

    @RepeatedTest(value = 3, name = RepeatedTest.SHORT_DISPLAY_NAME)
    public void test(RepetitionInfo info) {
        assertTrue(1 == 1);
        logger.info("Repetition #" + info.getCurrentRepetition());
    }
}
