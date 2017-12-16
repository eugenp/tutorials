package com.baeldung.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogLayoutsTest {

    private static Logger logger = LoggerFactory.getLogger("jsonLogger");
    
    @Test
    public void whenLogInJSON_thenFormatOK(){
        logger.debug("Debug message");
    }
}
