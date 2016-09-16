package com.baeldung.logging.log4j2.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class AsyncFileAppenderUsingJsonLayoutTest {

    @Rule
    public LoggerContextRule contextRule = new LoggerContextRule("log4j2-async-file-appender_json-layout_colored.xml");

    @Test
    public void givenLoggerWithAsyncConfig_shouldLogToJsonFile() throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is async JSON message #{} at INFO level.", count);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<LogEvent> logEvents = Files.readAllLines(Paths.get("target/logfile.json")).stream()
          .map(s -> {
              try {
                  return objectMapper.readValue(s.getBytes(), LogEvent.class);
              } catch (IOException e) {
                  throw new RuntimeException("Failed to import LogEvent!", e);
              }
          })
          .collect(Collectors.toList());
        assertTrue(logEvents.size() <= count);
    }
}
