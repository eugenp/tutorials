package com.baeldung.threading.request;

import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket socket;
    private final String request;

    public RequestHandler(Socket socket, String request) {
        this.socket = socket;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Thread.sleep(1000); // simulate server doing work
            logger.info("Processing request: {}", request);
            writer.println("HTTP/1.1 200 OK - Processed request: " + request);
            logger.info("Processed request: {}", request);
        } catch (Exception e) {
            logger.error("Error processing request: {}", e.getMessage());
        }
    }
}