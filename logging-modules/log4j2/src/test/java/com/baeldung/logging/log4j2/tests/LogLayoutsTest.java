package com.baeldung.logging.log4j2.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;

public class LogLayoutsTest {
    
    private static Logger logger = LogManager.getLogger("CONSOLE_JSON_APPENDER");
    
    @Test
    public void whenLogInJSON_thenFormatOK(){
        logger.debug("Debug message");
    }
}
