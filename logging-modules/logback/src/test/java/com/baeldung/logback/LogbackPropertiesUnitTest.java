package com.baeldung.logback;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

public class LogbackPropertiesUnitTest {

    private static LoggerContext loggerContext;


    @BeforeAll
    public static void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback-properties.xml");
        loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    @Test
    public void whenUserPropertiesFile_thenReturnCorrectLevel() {
        Logger rootLogger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        assertEquals(Level.INFO, rootLogger.getLevel());

        Logger baeldungLogger = loggerContext.getLogger("com.baeldung.logback");
        assertEquals(Level.DEBUG, baeldungLogger.getLevel());

        Logger baeldungServicesLogger = loggerContext.getLogger("com.baeldung.logback.services");
        assertEquals(Level.ERROR, baeldungServicesLogger.getLevel());
    }
}
