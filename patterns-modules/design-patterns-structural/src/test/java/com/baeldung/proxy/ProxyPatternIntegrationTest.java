package com.baeldung.proxy;

import static com.baeldung.util.LoggerUtil.LOG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProxyPatternIntegrationTest {
    public static TestAppenderDP appender;
    
    @Before
    public void setUp() {
        appender = new TestAppenderDP();
        LOG.addAppender(appender);
    }
    
    @Test
    public void givenExpensiveObjectProxy_WhenObjectInitialized_thenInitializedOnlyOnce() {
        ExpensiveObject object = new ExpensiveObjectProxy();
        object.process();
        object.process();

        final List<LoggingEvent> log = appender.getLog();
        
        assertThat((String) log.get(0).getMessage(), is("Loading initial configuration.."));
        assertThat((String) log.get(1).getMessage(), is("processing complete."));
        assertThat((String) log.get(2).getMessage(), is("processing complete."));
    }
    
    @After
    public void tearDown() {
        LOG.removeAppender(appender);
    }
}

