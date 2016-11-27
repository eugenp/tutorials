package com.baeldung.log4j;

import java.util.stream.IntStream;

import org.apache.log4j.Logger;

public class Log4jRollingExample {

    private final static Logger logger = Logger.getLogger(Log4jRollingExample.class);

    public static void main(String[] args) {
        IntStream.range(0, 1000).forEach(i -> {
            logger.info("This is the " + i + " time I say 'Hello World'.");
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                // should not happen
            }
        });
    }

}
