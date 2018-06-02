package com.baeldung.logging.log4j2.tests;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.baeldung.logging.log4j2.Log4j2BaseIntegrationTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONLayoutIntegrationTest extends Log4j2BaseIntegrationTest {

    private static Logger logger;
    private ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(consoleOutput);

    @Before
    public void setUp() {
        // Redirect console output to our stream
        System.setOut(ps);
        logger = LogManager.getLogger("CONSOLE_JSON_APPENDER");
    }

    @Test
    public void whenLogLayoutInJSON_thenOutputIsCorrectJSON() {
        logger.debug("Debug message");
        String currentLog = consoleOutput.toString();
        assertTrue(!currentLog.isEmpty() && isValidJSON(currentLog));
    }

    public static boolean isValidJSON(String jsonInString) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}