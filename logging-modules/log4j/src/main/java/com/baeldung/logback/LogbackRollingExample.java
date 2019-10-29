package com.baeldung.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class LogbackRollingExample {

    private static final Logger logger = LoggerFactory.getLogger(LogbackRollingExample.class);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i<2000; i++){
            logger.info("This is the {} time I say 'Hello World'.", i);
            Thread.sleep(100);
        }
    }

}
