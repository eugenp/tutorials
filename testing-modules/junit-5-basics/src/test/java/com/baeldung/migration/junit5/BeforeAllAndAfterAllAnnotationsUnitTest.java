package com.baeldung.migration.junit5;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnitPlatform.class)
public class BeforeAllAndAfterAllAnnotationsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeAllAndAfterAllAnnotationsUnitTest.class);
    
    @BeforeAll
    public static void setup() {
        LOG.info("startup - creating DB connection");
    }

    @AfterAll
    public static void tearDown() {
        LOG.info("closing DB connection");
    }

    @Test
    public void simpleTest() {
        LOG.info("simple test");
    }

    @Test
    public void anotherSimpleTest() {
        LOG.info("another simple test");
    }
}
