package com.baeldung.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class Slf4jRollingExample {

    private static Logger logger = LoggerFactory.getLogger(Slf4jRollingExample.class);

    public static void main(String[] args) throws InterruptedException {
        for(int i = 0; i<2000; i++){
            logger.info("This is the {} time I say 'Hello World'.", i);
            Thread.sleep(100);
        }
    }

}
