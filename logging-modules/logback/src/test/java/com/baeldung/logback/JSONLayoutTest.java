package com.baeldung.logback;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONLayoutTest {

    private static Logger logger;
    private ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private PrintStream ps = new PrintStream(consoleOutput);

    @Before
    public void setUp() {
        // Redirect console output to our stream
        System.setOut(ps);
        logger = LoggerFactory.getLogger("jsonLogger");
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
