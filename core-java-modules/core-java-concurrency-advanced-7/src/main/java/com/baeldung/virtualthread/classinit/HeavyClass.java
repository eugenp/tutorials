package com.baeldung.virtualthread.classinit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeavyClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeavyClass.class);

    static {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        LOGGER.info("static initialization done");
    }

    {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }

        LOGGER.info("initialization done");
    }
}