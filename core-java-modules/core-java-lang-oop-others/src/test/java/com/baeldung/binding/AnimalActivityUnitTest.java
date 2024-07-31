package com.baeldung.binding;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

/**
 *https://gist.github.com/bloodredsun/a041de13e57bf3c6c040
 */
@RunWith(MockitoJUnitRunner.class)

public class AnimalActivityUnitTest {

    @Mock
    private Appender mockAppender;
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setup() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @After
    public void teardown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    public void givenAnimalReference__whenRefersAnimalObject_shouldCallFunctionWithAnimalParam() {

        Animal animal = new Animal();

        AnimalActivity.sleep(animal);

        verify(mockAppender).doAppend(captorLoggingEvent.capture());

        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        assertThat(loggingEvent.getLevel(), is(Level.INFO));

        assertThat(loggingEvent.getFormattedMessage(),
                is("Animal is sleeping"));
    }

    @Test
    public void givenDogReference__whenRefersCatObject_shouldCallFunctionWithAnimalParam() {

        Dog dog = new Dog();

        AnimalActivity.sleep(dog);

        verify(mockAppender).doAppend(captorLoggingEvent.capture());

        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        assertThat(loggingEvent.getLevel(), is(Level.INFO));

        assertThat(loggingEvent.getFormattedMessage(),
                is("Cat is sleeping"));
    }

    @Test
    public void givenAnimaReference__whenRefersDogObject_shouldCallFunctionWithAnimalParam() {

        Animal cat = new Dog();

        AnimalActivity.sleep(cat);

        verify(mockAppender).doAppend(captorLoggingEvent.capture());

        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();

        assertThat(loggingEvent.getLevel(), is(Level.INFO));

        assertThat(loggingEvent.getFormattedMessage(),
                is("Animal is sleeping"));
    }
}