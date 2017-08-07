/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.logging.log4j2.tests.lambda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LambdaLoggingTest {
    
    @Test
    public void givenLoggerWithDefaultConfig_whenLogToConsole_thanOK() throws Exception {

        Logger logger = LogManager.getLogger(getClass());

        logger.debug("I am logging the number {} from expensive operation.", () -> expensiveOperation());

    }

    public int expensiveOperation() {
        
        int Number = 8;
        return Number;
    }

}
