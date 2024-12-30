package com.baeldung.junit.log;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

class BusinessWorkerUnitTest {

    private static MemoryAppender memoryAppender;
    private static final String LOGGER_NAME = "com.baeldung.junit.log";
    private static final String MSG = "This is a test message!!!";

    @BeforeEach
    void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @AfterEach
    void cleanUp() {
        memoryAppender.reset();
        memoryAppender.stop();
    }

    @Test
    void test() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs(MSG);

        // I check that I only have 4 messages (all but trace)
        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(4);
        // I look for a specific message at a specific level, and I only have 1
        assertThat(memoryAppender.search(MSG, Level.INFO)
            .size()).isEqualTo(1);
        // I check that the entry that is not present is the trace level
        assertThat(memoryAppender.contains(MSG, Level.TRACE)).isFalse();
    }

    @Test
    void whenMultipleLogLevel_thenReturnExpectedResult() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("Transaction started for Order ID: 1001");
        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(4);
        assertThat(memoryAppender.search("Transaction started", Level.INFO)
            .size()).isEqualTo(1);
        assertThat(memoryAppender.search("Transaction started", Level.WARN)
            .size()).isEqualTo(1);
        assertThat(memoryAppender.search("Transaction started", Level.ERROR)
            .size()).isEqualTo(1);
        assertThat(memoryAppender.search("Transaction started", Level.TRACE)).isEmpty();
    }

    @Test
    void whenUsingPattern_thenReturnExpectedResult() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("Order processed successfully for Order ID: 12345");

        Pattern orderPattern = Pattern.compile(".*Order ID: \\d{5}.*");

        assertThat(memoryAppender.containsPattern(orderPattern, Level.INFO)).isTrue();
        assertThat(memoryAppender.containsPattern(orderPattern, Level.WARN)).isTrue();
        assertThat(memoryAppender.containsPattern(orderPattern, Level.ERROR)).isTrue();
        assertThat(memoryAppender.containsPattern(orderPattern, Level.TRACE)).isFalse();
    }

    @Test
    void whenUsingMultiplePatterns_thenReturnExpectedResult() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("User Login: username=user123, timestamp=2024-11-25T10:15:30");

        List<Pattern> patterns = List.of(
            Pattern.compile(".*username=user\\w+.*"),
            Pattern.compile(".*timestamp=\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.*")
        );

        assertThat(memoryAppender.containsPatterns(patterns, Level.INFO)).isTrue();
        assertThat(memoryAppender.containsPatterns(patterns, Level.WARN)).isTrue();
    }

}