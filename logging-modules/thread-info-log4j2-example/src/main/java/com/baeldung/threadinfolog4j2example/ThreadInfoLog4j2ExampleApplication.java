package com.baeldung.threadinfolog4j2example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.IntStream;

@SpringBootApplication
public class ThreadInfoLog4j2ExampleApplication {
    private static final Logger LOGGER = LogManager.getLogger(ThreadInfoLog4j2ExampleApplication.class);
    
    public static void main(String[] args) {
        IntStream.range(0, 5).forEach(i -> {
            Runnable runnable = () -> LOGGER.info("Logging info to a specific thread");
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }
}
