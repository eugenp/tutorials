package com.baeldung.threading.request;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPerRequest extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPerRequest.class);

    private final PrintWriter writer;
    private final String request;

    public ThreadPerRequest(PrintWriter writer, String request) {
        this.writer = writer;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000); // simulate server doing work
            logger.info("Processing request: {}", request);
            writer.println("HTTP/1.1 200 OK - Processed request: " + request);
            logger.info("Processed request: {}", request);
        } catch (Exception e) {
            logger.error("Error processing request", e);
        }
    }
}