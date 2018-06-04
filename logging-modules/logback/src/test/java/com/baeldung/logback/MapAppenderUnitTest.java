package com.baeldung.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.BasicStatusManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapAppenderUnitTest {

    private LoggerContext ctx;

    private MapAppender mapAppender = new MapAppender();

    private LoggingEvent event;

    @Before
    public void setUp() throws Exception {
        ctx = new LoggerContext();
        ctx.setName("test context");
        ctx.setStatusManager(new BasicStatusManager());
        mapAppender.setContext(ctx);
        mapAppender.setPrefix("prefix");
        event = new LoggingEvent("fqcn", ctx.getLogger("logger"), Level.INFO, "Test message for logback appender", null, new Object[0]);
        ctx.start();
    }

    @After
    public void tearDown() throws Exception {
        ctx.stop();
        mapAppender.stop();
    }

    @Test
    public void whenPrefixIsNull_thenMapAppenderDoesNotLog() throws Exception {
        mapAppender.setPrefix(null);
        mapAppender.append(event);
        assertTrue(mapAppender.getEventMap().isEmpty());
    }

    @Test
    public void whenPrefixIsEmpty_thenMapAppenderDoesNotLog() throws Exception {
        mapAppender.setPrefix("");
        mapAppender.append(event);
        assertTrue(mapAppender.getEventMap().isEmpty());
    }

    @Test
    public void whenLogMessageIsEmitted_thenMapAppenderReceivesMessage() throws Exception {
        mapAppender.append(event);
        assertEquals(mapAppender.getEventMap().size(), 1);
        mapAppender.getEventMap().forEach((k, v) -> assertTrue(k.startsWith("prefix")));
    }

}
