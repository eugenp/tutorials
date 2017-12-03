package com.baeldung.logback;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

public class MapAppenderIntegrationTest {

    private Logger logger;

    @Before
    public void setUp() throws Exception {
        logger = LoggerFactory.getLogger("integrationTest");
    }

    @Test
    public void name() throws Exception {
        logger.info("Test from {}", this.getClass().getSimpleName());
        ch.qos.logback.classic.Logger rootLogger = 
          (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("ROOT");
        MapAppender appender = (MapAppender) rootLogger.getAppender("map");
        assertEquals(appender.getLoggingMap().size(), 1);
    }

}
