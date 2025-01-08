package com.baeldung.junit.log;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
class BusinessWorkerUnitTest {

    private static final MemoryAppender memoryAppender = new MemoryAppender();
    private static final String LOGGER_NAME = "com.baeldung.junit.log";
    private static final String MSG = "This is a test message!!!";

    @BeforeEach
    void setup() {
        Logger logger = (Logger) LoggerFactory.getLogger(LOGGER_NAME);
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);

        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        memoryAppender.start();
    }

    @AfterEach
    void cleanUp() {
        memoryAppender.reset();
        memoryAppender.stop();
    }

    @Test
    void whenLogsGenerated_thenMemoryAppenderContainsLogs() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs(MSG);

        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(4);
        assertThat(memoryAppender.search(MSG, Level.INFO)).hasSize(1);
        assertThat(memoryAppender.contains(MSG, Level.TRACE)).isFalse();
    }

    @Test
    void whenMultipleLogLevel_thenReturnExpectedResult() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("Transaction started for Order ID: 1001");

        assertThat(memoryAppender.countEventsForLogger(LOGGER_NAME)).isEqualTo(4);
        assertThat(memoryAppender.search("Transaction started", Level.DEBUG)).hasSize(1);
        assertThat(memoryAppender.search("Transaction started", Level.INFO)).hasSize(1);
        assertThat(memoryAppender.search("Transaction started", Level.WARN)).hasSize(1);
        assertThat(memoryAppender.search("Transaction started", Level.ERROR)).hasSize(1);
        assertThat(memoryAppender.search("Transaction started", Level.TRACE)).isEmpty();
    }

    @Test
    void whenUsingPattern_thenReturnExpectedResult() {
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs("Order processed successfully for Order ID: 12345");

        Pattern orderPattern = Pattern.compile(".*Order ID: \\d{5}.*");

        assertThat(memoryAppender.containsPattern(orderPattern, Level.DEBUG)).isTrue();
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

        assertThat(memoryAppender.containsPatterns(patterns, Level.DEBUG)).isTrue();
        assertThat(memoryAppender.containsPatterns(patterns, Level.INFO)).isTrue();
        assertThat(memoryAppender.containsPatterns(patterns, Level.WARN)).isTrue();
        assertThat(memoryAppender.containsPatterns(patterns, Level.ERROR)).isTrue();
        assertThat(memoryAppender.containsPatterns(patterns, Level.TRACE)).isFalse();
    }

    @Test
    void whenLogsGenerated_thenCapturedOutputContainsLogs(CapturedOutput capturedOutput) {
        String log = "Order processed successfully for Order ID: 12345.";
        BusinessWorker worker = new BusinessWorker();
        worker.generateLogs(log);

        assertThat(capturedOutput.getOut()).contains(log);
        assertThat(capturedOutput.getOut()).containsPattern(".*Order ID: \\d{5}.*");
    }

}