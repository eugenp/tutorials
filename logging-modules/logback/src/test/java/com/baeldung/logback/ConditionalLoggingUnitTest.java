package com.baeldung.logback;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class ConditionalLoggingUnitTest {

    @TempDir
    Path tempDir;

    private PrintStream originalOut;
    private ByteArrayOutputStream consoleOutput;

    @BeforeEach
    void setUp() {
        originalOut = System.out;
        consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput, true));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);

        // clear properties used across tests
        System.clearProperty("ENVIRONMENT");
        System.clearProperty("LOG_STASH_URL");
        System.clearProperty("LB_TEST_ENV");
        System.clearProperty("LB_TEST_HOST");

        // Windows-safe: stop & detach appenders so temp files can be deleted
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger root = ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        for (Iterator<Appender<ILoggingEvent>> it = root.iteratorForAppenders(); it.hasNext(); ) {
            Appender<ILoggingEvent> a = it.next();
            if (a instanceof FileAppender) {
                a.stop();
            }
            root.detachAppender(a);
        }

        ctx.stop();
        ctx.reset();
    }

    private void reconfigure(String classpathXml, String outputDir) throws Exception {
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        ctx.reset();

        if (outputDir != null) {
            ctx.putProperty("outputDir", outputDir);
        }

        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(ctx);
        configurator.doConfigure(getClass().getResource(classpathXml));
    }

    // --- Section 3.2 style tests (logback-conditional.xml)

    @Test
    void givenEnvironmentPropertyIsMissing_whenLogging_thenWritesToConsole() throws Exception {
        System.clearProperty("ENVIRONMENT");
        reconfigure("/logback-conditional.xml", tempDir.toString());

        Logger logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        logger.info("test console log");

        String out = new String(consoleOutput.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(out.contains("test console log"));

        Path logFile = tempDir.resolve("conditional.log");
        assertFalse(Files.exists(logFile));
    }

    @Test
    void givenEnvironmentPropertyIsProd_whenLogging_thenWritesToFile() throws Exception {
        System.setProperty("ENVIRONMENT", "PROD");
        reconfigure("/logback-conditional.xml", tempDir.toString());

        Logger logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        logger.info("test prod log");

        // flush + release lock before reading
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger root = ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        @SuppressWarnings("unchecked")
        FileAppender<ILoggingEvent> fileAppender =
          (FileAppender<ILoggingEvent>) root.getAppender("FILE");
        assertNotNull(fileAppender);
        fileAppender.stop();

        Path logFile = tempDir.resolve("conditional.log");
        assertTrue(Files.exists(logFile));

        String content = new String(Files.readAllBytes(logFile), StandardCharsets.UTF_8);
        assertTrue(content.contains("test prod log"));

        String out = new String(consoleOutput.toByteArray(), StandardCharsets.UTF_8);
        assertFalse(out.contains("test prod log"));
    }

    // --- Section 3.3 Boolean Expression Conditions tests (logback-expression.xml)

    @Test
    void givenEnvIsNullAndHostnameIsTorino_whenLogging_thenUsesConsoleBranch() throws Exception {
        System.clearProperty("LB_TEST_ENV"); // ensure null
        System.setProperty("LB_TEST_HOST", "torino");
        reconfigure("/logback-expression.xml", tempDir.toString());

        Logger logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        logger.info("console-branch");

        String out = new String(consoleOutput.toByteArray(), StandardCharsets.UTF_8);
        assertTrue(out.contains("console-branch"));

        Path logFile = tempDir.resolve("conditional.log");
        assertFalse(Files.exists(logFile));
    }

    @Test
    void givenEnvIsNotNullOrHostnameIsNotTorino_whenLogging_thenUsesFileBranch() throws Exception {
        System.setProperty("LB_TEST_ENV", "anything");
        System.setProperty("LB_TEST_HOST", "torino");
        reconfigure("/logback-expression.xml", tempDir.toString());

        Logger logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        logger.info("file-branch");

        // flush + release lock
        LoggerContext ctx = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger root = ctx.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        @SuppressWarnings("unchecked")
        FileAppender<ILoggingEvent> fileAppender =
          (FileAppender<ILoggingEvent>) root.getAppender("FILE");
        assertNotNull(fileAppender);
        fileAppender.stop();

        Path logFile = tempDir.resolve("conditional.log");
        assertTrue(Files.exists(logFile));

        String content = new String(Files.readAllBytes(logFile), StandardCharsets.UTF_8);
        assertTrue(content.contains("file-branch"));

        String out = new String(consoleOutput.toByteArray(), StandardCharsets.UTF_8);
        assertFalse(out.contains("file-branch"));
    }
}