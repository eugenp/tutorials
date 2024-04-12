package com.baeldung.logging.log4j2.tests;

import static org.junit.Assert.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.ThreadContext;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.baeldung.logging.log4j2.tests.jdbc.ConnectionFactory;

@RunWith(JUnit4.class)
public class CustomLoggingIntegrationTest {

    private static String logFilePath = System.getProperty("logging.folder.path");
    
    @BeforeClass
    public static void setup() throws Exception {
        
        Connection connection = ConnectionFactory.getConnection();
        connection.createStatement()
          .execute("CREATE TABLE logs(" + "when TIMESTAMP," + "logger VARCHAR(255)," + "level VARCHAR(255)," + "message VARCHAR(4096)," + "throwable TEXT)");
        connection.commit();
    }

    @Test
    public void givenLoggerWithDefaultConfig_whenLogToConsole_thenOK() throws Exception {
        Logger logger = LogManager.getLogger(getClass());
        Exception e = new RuntimeException("This is only a test!");

        logger.info("This is a simple message at INFO level. " + "It will be hidden.");
        logger.error("This is a simple message at ERROR level. " + "This is the minimum visible level.", e);
    }

    @Test
    public void givenLoggerWithConsoleConfig_whenLogToConsoleInColors_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("CONSOLE_PATTERN_APPENDER_MARKER");
        Exception e = new RuntimeException("This is only a test!");

        logger.trace("This is a colored message at TRACE level.");
        logger.debug("This is a colored message at DEBUG level. " + "This is the minimum visible level.");
        logger.info("This is a colored message at INFO level.");
        logger.warn("This is a colored message at WARN level.");
        logger.error("This is a colored message at ERROR level.", e);
        logger.fatal("This is a colored message at FATAL level.");
    }

    @Test
    public void givenLoggerWithConsoleConfig_whenFilterByMarker_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("CONSOLE_PATTERN_APPENDER_MARKER");
        Marker appError = MarkerManager.getMarker("APP_ERROR");
        Marker connectionTrace = MarkerManager.getMarker("CONN_TRACE");

        logger.error(appError, "This marker message at ERROR level should be hidden.");
        logger.trace(connectionTrace, "This is a marker message at TRACE level.");
    }

    @Test
    public void givenLoggerWithConsoleConfig_whenFilterByThreadContext_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("CONSOLE_PATTERN_APPENDER_THREAD_CONTEXT");
        ThreadContext.put("userId", "1000");
        logger.info("This is a log-visible user login. Maybe from an admin account?");
        ThreadContext.put("userId", "1001");
        logger.info("This is a log-invisible user login.");
    }

    @Test
    public void givenLoggerWithAsyncConfig_whenLogToJsonFile_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("ASYNC_JSON_FILE_APPENDER");

        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is async JSON message #{} at INFO level.", count);
        }

        long logEventsCount = Files.lines(Paths.get(logFilePath))
                .count();
        
        assertTrue(logEventsCount >= 0 && logEventsCount <= count);
    }

    @Test
    public void givenLoggerWithFailoverConfig_whenLog_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("FAIL_OVER_SYSLOG_APPENDER");
        Exception e = new RuntimeException("This is only a test!");

        logger.trace("This is a syslog message at TRACE level.");
        logger.debug("This is a syslog message at DEBUG level.");
        logger.info("This is a syslog message at INFO level. This is the minimum visible level.");
        logger.warn("This is a syslog message at WARN level.");
        logger.error("This is a syslog message at ERROR level.", e);
        logger.fatal("This is a syslog message at FATAL level.");
    }

    @Test
    public void givenLoggerWithJdbcConfig_whenLogToDataSource_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("JDBC_APPENDER");

        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is JDBC message #{} at INFO level.", count);
        }
        Connection connection = ConnectionFactory.getConnection();
        ResultSet resultSet = connection.createStatement()
          .executeQuery("SELECT COUNT(*) AS ROW_COUNT FROM logs");

        int logCount = 0;
        if (resultSet.next()) {
            logCount = resultSet.getInt("ROW_COUNT");
        }
        assertTrue(logCount <= count);
    }

    @Test
    public void givenLoggerWithRollingFileConfig_whenLogToXMLFile_thenOK() throws Exception {
        Logger logger = LogManager.getLogger("XML_ROLLING_FILE_APPENDER");

        final int count = 88;
        for (int i = 0; i < count; i++) {
            logger.info("This is rolling file XML message #{} at INFO level.", i);
        }
    }

}
