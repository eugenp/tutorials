package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FileAppenderUsingJsonLayoutWithBurstFilterTest {

    @Rule
    public LoggerContextRule contextRule = new LoggerContextRule("log4j2-file-appender_json-layout_burst-filter.xml");

    @Test
    public void givenLoggerWithBurstFileConfig_shouldLogToJsonFile() throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is burst JSON message #{} at INFO level.", count);
            TimeUnit.MILLISECONDS.sleep(100);
        }
        long logEventsCount = Files.lines(Paths.get("target/logfile-burst.json")).count();
        assertTrue(logEventsCount > 0 && logEventsCount < count);
    }
}
