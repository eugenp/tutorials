package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.junit.LoggerContextRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FailoverSyslogConsoleAppenderTest {
    @Rule
    public LoggerContextRule contextRule = 
      new LoggerContextRule("log4j2-failover-syslog-console-appender_pattern-layout.xml");

    @Test
    public void givenLoggerWithFailoverConfig_shouldLog() throws Exception {
        Logger logger = contextRule.getLogger(getClass().getSimpleName());
        logger.trace("This is a syslog message at TRACE level.");
        logger.debug("This is a syslog message at DEBUG level.");
        logger.info("This is a syslog message at INFO level. This is the minimum visible level.");
        logger.warn("This is a syslog message at WARN level.");
        Exception e = new RuntimeException("This is only a test!");
        logger.error("This is a syslog message at ERROR level.", e);
        logger.fatal("This is a syslog message at FATAL level.");
    }
}
