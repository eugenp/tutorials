package com.baeldung.test.cdi;

import com.baeldung.cdi.factories.TimeLoggerFactory;
import com.baeldung.cdi.loggers.TimeLogger;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TimeLoggerFactoryUnitTest {
    
    @Test
    public void givenTimeLoggerFactory_whenCalledgetTimeLogger_thenOneAssertion() {
        TimeLoggerFactory timeLoggerFactory = new TimeLoggerFactory();
        assertThat(timeLoggerFactory.getTimeLogger()).isInstanceOf(TimeLogger.class);
    }
}