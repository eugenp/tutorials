package com.baeldung.designpatterns;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.designpatterns.bridge.Blue;
import com.baeldung.designpatterns.bridge.Red;
import com.baeldung.designpatterns.bridge.Shape;
import com.baeldung.designpatterns.bridge.Square;
import com.baeldung.designpatterns.bridge.Triangle;

public class BridgePatternIntegrationTest {
    public static TestAppenderDP appender;
    
    @Before
    public void setUp() {
        appender = new TestAppenderDP();
        LOG.addAppender(appender);
    }
    
    @Test
    public void whenBridgePatternInvoked_thenConfigSuccess() {
        //a square with red color
        Shape square = new Square(new Red());
        square.drawShape();
        
        //a triangle with blue color
        Shape triangle = new Triangle(new Blue());
        triangle.drawShape();

        final List<LoggingEvent> log = appender.getLog();
        
        assertThat((String) log.get(0).getMessage(), is("Square drawn. "));
        assertThat((String) log.get(1).getMessage(), is("Color : Red"));
        assertThat((String) log.get(2).getMessage(), is("Triangle drawn. "));
        assertThat((String) log.get(3).getMessage(), is("Color : Blue"));
    }
    
    @After
    public void tearDown() {
        LOG.removeAppender(appender);
    }
}

