package com.baeldung.logback;

import ch.qos.logback.classic.Logger;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class MapAppenderIntegrationTest {

    private Logger rootLogger;

    @Before
    public void setUp() throws Exception {
        rootLogger = (Logger) LoggerFactory.getLogger("ROOT");
    }

    @Test
    public void whenLoggerEmitsLoggingEvent_thenAppenderReceivesEvent() throws Exception {
        rootLogger.info("Test from {}", this.getClass().getSimpleName());
        MapAppender appender = (MapAppender) rootLogger.getAppender("map");
        assertEquals(appender.getEventMap().size(), 1);
    }

    @Test
    public void givenNoPrefixSet_whenLoggerEmitsEvent_thenAppenderReceivesNoEvent() throws Exception {
        rootLogger.info("Test from {}", this.getClass().getSimpleName());
        MapAppender appender = (MapAppender) rootLogger.getAppender("badMap");
        assertEquals(appender.getEventMap().size(), 0);
    }

}
