package com.baeldung.junit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.logging.Logger;

public class TestAnnotationsUnitTest {

    private static final Logger log = Logger.getLogger(TestAnnotationsUnitTest.class.getName());

    @BeforeClass
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class");
    }

    @Before
    void init() {
        log.info("@BeforeEach - executes before each test method in this class");
    }

    @After
    void tearDown() {
        log.info("@AfterEach - executed after each test method.");
    }

    @AfterClass
    static void done() {
        log.info("@AfterAll - executed after all test methods.");
    }

}
