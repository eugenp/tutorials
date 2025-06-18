package com.baeldung.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class MyCustomEvaluatorUnitTest {

    private static Logger logger;
    
    @BeforeAll
    public static void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback-evaluator.xml");
    }
    
    @Test
    public void givenCustomEvaluatorFilter_whenEvaluatingContainsBillingInformation_thenEvaluationSuccessful() {
        MyCustomEvaluator evaluator = new MyCustomEvaluator();
        logger = (Logger) LoggerFactory.getLogger(MyCustomEvaluatorUnitTest.class);
        LoggingEvent event = new LoggingEvent("fqcn", logger, Level.INFO, "This message contains billing information.", null, null);
        assertTrue(evaluator.evaluate(event));
    }

    @Test
    public void givenCustomEvaluatorFilter_whenEvaluatingDoesNotContainBillingInformation_thenEvaluationSuccessful() {
        MyCustomEvaluator evaluator = new MyCustomEvaluator();
        logger = (Logger) LoggerFactory.getLogger(MyCustomEvaluatorUnitTest.class);
        LoggingEvent event = new LoggingEvent("fqcn", logger, Level.INFO, "This message does not.", null, null);
        assertFalse(evaluator.evaluate(event));
    }
}
