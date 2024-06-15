package com.baeldung.logback.disableclass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerboseClass {

    private static final Logger logger = LoggerFactory.getLogger(VerboseClass.class);

    public void process() {
        logger.info("Processing data in VerboseClass...");
    }

    public static void main(String[] args) {
        VerboseClass instance = new VerboseClass();
        instance.process();
        logger.info("Main method completed in VerboseClass");
    }
}
