package com.baeldung.systemrules;

import static com.github.stefanbirkner.systemlambda.SystemLambda.restoreSystemProperties;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ProvidesSystemPropertyUnitTest {

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.setProperty("log_dir", "/tmp/baeldung/logs");
    }

    @Test
    void givenSetSystemProperty_whenGetLogDir_thenLogDirIsProvidedSuccessfully() throws Exception {
        restoreSystemProperties(() -> {
            System.setProperty("log_dir", "test/resources");
            assertEquals("log_dir should be provided", "test/resources", System.getProperty("log_dir"));
        });

        assertEquals("log_dir should be provided", "/tmp/baeldung/logs", System.getProperty("log_dir"));
    }

}
