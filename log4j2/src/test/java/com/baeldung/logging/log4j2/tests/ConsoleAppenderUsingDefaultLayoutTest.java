package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConsoleAppenderUsingDefaultLayoutTest {
    @Test
    public void givenLoggerWithDefaultConfig_shouldLogToConsole()
      throws Exception {
        Logger logger = LogManager.getLogger(getClass());
        Exception e = new RuntimeException("This is only a test!");
        logger.info("This is a simple message at INFO level. " +
          "It will be hidden.");
        logger.error("This is a simple message at ERROR level. " +
          "This is the minimum visible level.", e);
    }
}
