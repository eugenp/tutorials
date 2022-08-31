package com.baeldung.migration.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeforeAllAndAfterAllAnnotationsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeAllAndAfterAllAnnotationsUnitTest.class);
    
    @BeforeAll
    public static void setup() {
        LOG.debug("startup - creating DB connection");
    }

    @AfterAll
    public static void tearDown() {
        LOG.debug("closing DB connection");
    }

    @Test
    public void simpleTest() {
        LOG.debug("simple test");
    }

    @Test
    public void anotherSimpleTest() {
        LOG.debug("another simple test");
    }
}
