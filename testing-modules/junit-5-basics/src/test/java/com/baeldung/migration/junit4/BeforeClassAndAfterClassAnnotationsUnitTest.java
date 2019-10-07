package com.baeldung.migration.junit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(JUnit4.class)
public class BeforeClassAndAfterClassAnnotationsUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(BeforeClassAndAfterClassAnnotationsUnitTest.class);
    
    @BeforeClass
    public static void setup() {
        LOG.info("startup - creating DB connection");
    }

    @AfterClass
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
