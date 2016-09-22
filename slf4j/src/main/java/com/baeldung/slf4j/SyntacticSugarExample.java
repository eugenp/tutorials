package com.baeldung.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyntacticSugarExample {

    private static Logger logger = LoggerFactory.getLogger(Slf4jExample.class);

    public static void main(String[] args) {
        String variable = "Hello John";
        if (logger.isDebugEnabled()) {
            logger.debug("Printing variable value: " + variable);
        }
    }
}
