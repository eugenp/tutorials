package com.baeldung.logback;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.Level;


public class ConditionalLoggingUnitTest {

    private static Logger logger;
    private static ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
    private static PrintStream printStream = new PrintStream(consoleOutput);

    @BeforeAll
    public static void setUp() {
        System.setProperty("logback.configurationFile", "src/test/resources/logback-conditional.xml");
        // Redirect console output to our stream
        System.setOut(printStream);
    }
    
    @Test
    public void whenSystemPropertyIsNotPresent_thenReturnConsoleLogger() {
        System.clearProperty("ENVIRONMENT");
        logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        
        logger.info("test console log");
        String logOutput = consoleOutput.toString();
        assertTrue(logOutput.contains("test console log"));
    }

    @Test
    public void whenSystemPropertyIsPresent_thenReturnFileLogger() throws IOException {
        System.setProperty("ENVIRONMENT", "PROD");
        logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);

        logger.info("test prod log");
        String logOutput = FileUtils.readFileToString(new File("conditional.log"));
        assertTrue(logOutput.contains("test prod log"));
    }

    @Test
    public void whenMatchedWithEvaluatorFilter_thenReturnFilteredLogs() throws IOException {
        logger = (Logger) LoggerFactory.getLogger(ConditionalLoggingUnitTest.class);
        
        logger.info("normal log");
        logger.info("billing details: XXXX");
        String normalLog = FileUtils.readFileToString(new File("conditional.log"));
        assertTrue(normalLog.contains("normal log"));
        assertTrue(normalLog.contains("billing details: XXXX"));
        
        String filteredLog = FileUtils.readFileToString(new File("filtered.log"));
        assertTrue(filteredLog.contains("test prod log"));
        //assertFalse(filteredLog.contains("billing details: XXXX"));
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
}
