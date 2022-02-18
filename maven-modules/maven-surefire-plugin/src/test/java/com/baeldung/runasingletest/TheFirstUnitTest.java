package com.baeldung.runasingletest;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TheFirstUnitTest {
    private static final Logger logger = LoggerFactory.getLogger(TheFirstUnitTest.class);

    @Test
    void whenTestCase_thenPass() {
        logger.info("Running a dummyTest");
    }
}
