package com.baeldung.logback;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

public class ConditionalLoggingUnitTest {

    private static Logger logger;
    private static ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private static PrintStream printStream = new PrintStream(consoleOutput);

    @BeforeAll
    public static void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback-conditional.xml");
        // Redirect console output to our stream
        System.setOut(printStream);
    }
    
    @Test
    public void whenSystemPropertyIsNotPresent_thenReturnConsoleLogger() {
        System.clearProperty("ENVIRONMENT");
        logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        
        logger.info("test console log");
        String logOutput = consoleOutput.toString();
        assertTrue(logOutput.contains("test console log"));
    }

    @Test
    public void whenSystemPropertyIsPresent_thenReturnFileLogger() throws IOException {
        System.setProperty("ENVIRONMENT", "PROD");
        logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);

        logger.info("test prod log");
        String logOutput = FileUtils.readFileToString(new File("conditional.log"));
        assertTrue(logOutput.contains("test prod log"));
    }

    @Test
    public void whenMatchedWithEvaluatorFilter_thenReturnFilteredLogs() throws IOException {
        System.setProperty("ENVIRONMENT", "PROD");
        logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        logger.info("billing details: XXXX");
        logger.info("test prod log");
        
        String filteredLog = FileUtils.readFileToString(new File("filtered.log"));
        assertTrue(filteredLog.contains("test prod log"));
        assertFalse(filteredLog.contains("billing details: XXXX"));
    }
}
