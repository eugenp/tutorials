package com.baeldung.log4j2;

import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2RollingExample {

    private static final Logger logger = LogManager.getLogger(Log4j2RollingExample.class);

    public static void main(String[] args) {
        IntStream.range(1, 100).forEach(i -> {
            writeLogLine(i);
        });
    }

    private static void writeLogLine(int i) {
        logger.info("This is the {} time I say 'Hello World'.", i);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // should not happen
        }
    }

}
