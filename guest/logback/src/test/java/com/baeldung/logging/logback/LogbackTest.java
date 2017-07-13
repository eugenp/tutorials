package com.baeldung.logging.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentMatcher;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class LogbackTest {

    @Test
    public void givenLoggerWithConfig_whenLogToConsole_thenOK() throws Exception {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);

        final Appender mockAppender = mock(Appender.class);
        final String logMessage = "This is only some info log message.";
        root.addAppender(mockAppender);

        ForecastLogger.logWeatherConditions(logMessage);

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent)argument).getFormattedMessage().contains(logMessage);
            }
        }));
    }

    @Test
    public void givenLoggerWithConfig_whenLogLevelError_thenOK() throws Exception {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);

        final Appender mockAppender = mock(Appender.class);
        final String errorMessage = "Some error log message.";
        root.addAppender(mockAppender);

        ForecastLogger.logWeatherSystemError(errorMessage);

        verify(mockAppender).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent)argument).getLevel().toInt() == Level.ERROR_INT;
            }
        }));
    }

    @Test
    public void givenLoggerWithMarkerAndFilterConfig_whenLogLevelInfo_thenDrop() throws Exception {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory
                .getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        final Appender mockAppender = mock(Appender.class);
        final String logMessage = "Some important log message.";

        root.addAppender(mockAppender);
        Marker marker = MarkerFactory.getMarker("TRIGGER_FILTER");

        ForecastLogger.markedLogWeatherEvent(logMessage, marker);

        verify(mockAppender, times(1)).doAppend(argThat(new ArgumentMatcher() {
            @Override
            public boolean matches(final Object argument) {
                return ((LoggingEvent)argument).getFormattedMessage().contains(logMessage);
            }
        }));
    }


}
