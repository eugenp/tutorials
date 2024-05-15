package com.baeldung.logging.log4j2.appender;

import static org.junit.Assert.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MapAppenderIntegrationTest {

    private Logger logger;

    @Before
    public void setup() {
        logger = LogManager.getLogger(MapAppenderIntegrationTest.class);
    }

    @Test
    public void whenLoggerEmitsLoggingEvent_thenAppenderReceivesEvent() throws Exception {
        LoggerContext context = LoggerContext.getContext(false);
        Configuration config = context.getConfiguration();
        MapAppender appender = config.getAppender("MapAppender");
        int eventSizeBeforeLog = appender.getEventMap()
          .size();
        logger.error("Error log message from {}", this.getClass()
          .getSimpleName());
        assertEquals(appender.getEventMap()
          .size(), ++eventSizeBeforeLog);
    }

}
