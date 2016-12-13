package com.baeldung.log4j;

import java.util.stream.IntStream;

import org.apache.log4j.Logger;

public class Log4jRollingExample {

    private final static Logger logger = Logger.getLogger(Log4jRollingExample.class);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i<2000; i++){
            logger.info("This is the " + i + " time I say 'Hello World'.");
            Thread.sleep(100);
        }
    }

}
