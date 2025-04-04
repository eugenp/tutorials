package com.baeldung.threading.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPerConnectionServer {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPerConnectionServer.class);

    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port {}", PORT);
            while (!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    logger.info("New client connected: {}", clientSocket.getInetAddress());
                    new ConnectionHandler(clientSocket).start();
                } catch (IOException e) {
                    logger.error("Error accepting connection: {}", e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.error("Error starting server: {}", e.getMessage());
        }
    }
}