package com.baeldung.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

public class MyCustomEvaluatorTest {

    @BeforeAll
    public static void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback-evaluator.xml");
    }
    
    @Test
    public void testEvaluate_containsBilling() {
        MyCustomEvaluator evaluator = new MyCustomEvaluator();
        LoggingEvent event = new LoggingEvent("fqcn", null, Level.INFO, "This message contains billing information.", null, null);
        assertTrue(evaluator.evaluate(event));
    }

    @Test
    public void testEvaluate_doesNotContainBilling() {
        MyCustomEvaluator evaluator = new MyCustomEvaluator();
        LoggingEvent event = new LoggingEvent("fqcn", null, Level.INFO, "This message does not.", null, null);
        assertFalse(evaluator.evaluate(event));
    }
}
