package com.baeldung.component.autoproxying;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {NotEligibleForAutoProxyRandomIntProcessor.class, DataCache.class, RandomIntGenerator.class})
public class NotEligibleForAutoProxyingIntegrationTest {
    private static MemoryLogAppender memoryAppender;

    private NotEligibleForAutoProxyRandomIntProcessor proxyRandomIntProcessor;

    @Autowired
    private DataCache dataCache;

    @BeforeClass
    public static void setup() {
        memoryAppender = new MemoryLogAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());

        Logger logger = (Logger) LoggerFactory.getLogger("org.springframework.context");
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryAppender);
        memoryAppender.start();
    }

    @Test
    public void givenAutowireInBeanPostProcessor_whenSpringContextInitialize_thenNotEligibleLogShouldShowAndGroupFieldNotPopulated() {
        List<ILoggingEvent> notEligibleEvents = memoryAppender.search("Bean 'randomIntGenerator' of type [com.baeldung.autoproxying.RandomIntGenerator] " +
          "is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)");

        assertEquals(1, notEligibleEvents.size());
        assertEquals(0, dataCache.getGroup());
    }
}
