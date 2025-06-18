package com.baeldung.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;

public class MyCustomEvaluatorUnitTest {

    private static Logger logger;
    private static ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private static PrintStream printStream = new PrintStream(consoleOutput);
    
    @BeforeAll
    public static void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback-evaluator.xml");
        System.setOut(printStream);
    }
    
    @Test
    public void testEvaluate_containsBilling() {
        MyCustomEvaluator evaluator = new MyCustomEvaluator();
        logger = (Logger) LoggerFactory.getLogger(MyCustomEvaluatorUnitTest.class);
        LoggingEvent event = new LoggingEvent("fqcn", logger, Level.INFO, "This message contains billing information.", null, null);
        assertTrue(evaluator.evaluate(event));
    }

    @Test
    public void testEvaluate_doesNotContainBilling() {
        MyCustomEvaluator evaluator = new MyCustomEvaluator();
        logger = (Logger) LoggerFactory.getLogger(MyCustomEvaluatorUnitTest.class);
        LoggingEvent event = new LoggingEvent("fqcn", logger, Level.INFO, "This message does not.", null, null);
        assertFalse(evaluator.evaluate(event));
    }
}
