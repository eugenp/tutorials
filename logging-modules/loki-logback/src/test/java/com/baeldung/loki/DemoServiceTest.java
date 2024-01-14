package com.baeldung.loki;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = DemoService.class)
public class DemoServiceTest {

    @Mock
    Appender<ILoggingEvent> appender;

    @Captor
    ArgumentCaptor<ILoggingEvent> captor;

    @Test
    public void givenLokiAppender_whenSampleServiceInvoked_ThenCaptureInfoLog() throws ParseException {
        ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        when(appender.getName()).thenReturn("loki-logback-appender");
        logger.addAppender(appender);

        DemoService service = new DemoService();
        service.log();

        verify(appender, times(1)).doAppend(captor.capture());
        List<ILoggingEvent> allValues = captor.getAllValues();
        assertEquals(1, allValues.size());
        ILoggingEvent event = allValues.get(0);
        assertEquals(event.getLevel(), Level.INFO);
        assertEquals("DemoService.log invoked", event.getFormattedMessage());
    }
}
