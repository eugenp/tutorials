package com.baeldung.threading.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.threading.ClientConnection;

public class ThreadPerConnection extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPerConnection.class);

    private final ClientConnection clientConnection;

    public ThreadPerConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

    @Override
    public void run() {
        try (ClientConnection client = this.clientConnection) {
            String request;
            while ((request = client.getReader()
                .readLine()) != null) {
                Thread.sleep(1000); // simulate server doing work
                logger.info("Processing request: {}", request);
                clientConnection.getWriter()
                    .println("HTTP/1.1 200 OK - Processed request: " + request);
                logger.info("Processed request: {}", request);
            }
        } catch (Exception e) {
            logger.error("Error processing request", e);
        }
    }
}
