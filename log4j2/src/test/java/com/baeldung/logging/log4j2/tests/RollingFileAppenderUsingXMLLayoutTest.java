package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class RollingFileAppenderUsingXMLLayoutTest {

    @Rule
    public LoggerContextRule contextRule = new LoggerContextRule("log4j2-rolling-file-appender_xml-layout.xml");

    @Test
    public void givenLoggerWithRollingFileConfig_shouldLogToXMLFile() throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is rolling file XML message #{} at INFO level.", i);
        }
        String[] logEvents = Files.readAllLines(Paths.get("target/logfile.xml")).stream()
          .collect(Collectors.joining(System.lineSeparator()))
          .split("\\n\\n+");
        assertTrue(logEvents.length == count);
    }
}
