package com.baeldung.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2RollingExample {

    private static final Logger logger = LogManager.getLogger(Log4j2RollingExample.class);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i<2000; i++){
            logger.info("This is the {} time I say 'Hello World'.", i);
            Thread.sleep(100);
        }
        LogManager.shutdown();
    }

}
