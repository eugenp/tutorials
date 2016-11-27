package com.baeldung.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class LogbackRollingExample {

    private static final Logger logger = LoggerFactory.getLogger(LogbackRollingExample.class);

    public static void main(String[] args) {
        IntStream.range(1, 100).forEach(i -> {
            logger.info("This is the {} time I say 'Hello World'.", i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // should not happen
            }
        });
    }

}
