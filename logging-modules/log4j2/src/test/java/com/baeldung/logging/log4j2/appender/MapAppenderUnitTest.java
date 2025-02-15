package com.baeldung.logging.log4j2.appender;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


//@Disabled
class MapAppenderUnitTest {

    private Logger logger;

    @BeforeEach
    void setup() {
        logger = LogManager.getLogger(MapAppenderUnitTest.class);
    }

    @Test
    void whenLoggerEmitsLoggingEvent_thenAppenderReceivesEvent() {
        LoggerContext context = LoggerContext.getContext(false);
        Configuration config = context.getConfiguration();
        MapAppender appender = config.getAppender("MapAppender");
        int eventSizeBeforeLog = appender.getEventMap().size();

        logger.error("Error log message from {}", this.getClass().getSimpleName());

        assertEquals(eventSizeBeforeLog + 1, appender.getEventMap().size());
    }
}
