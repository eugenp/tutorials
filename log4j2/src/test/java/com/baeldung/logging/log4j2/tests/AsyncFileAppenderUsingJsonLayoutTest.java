package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class AsyncFileAppenderUsingJsonLayoutTest {
    @Rule
    public LoggerContextRule contextRule =
      new LoggerContextRule("log4j2-async-file-appender_json-layout.xml");

    @Test
    public void givenLoggerWithAsyncConfig_shouldLogToJsonFile()
      throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is async JSON message #{} at INFO level.", count);
        }
        long logEventsCount = Files.lines(Paths.get("target/logfile.json")).count();
        assertTrue(logEventsCount > 0 && logEventsCount <= count);
    }
}
