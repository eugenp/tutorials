package com.baeldung.test.dependency-injection;

import com.baeldung.dependency-injection.factories.TimeLoggerFactory;
import com.baeldung.dependency-injection.loggers.TimeLogger;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class TimeLoggerFactoryUnitTest {
    
    @Test
    public void givenTimeLoggerFactory_whenCalledgetTimeLogger_thenOneAssertion() {
        TimeLoggerFactory timeLoggerFactory = new TimeLoggerFactory();
        assertThat(timeLoggerFactory.getTimeLogger()).isInstanceOf(TimeLogger.class);
    }
}