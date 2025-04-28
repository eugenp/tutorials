package com.baeldung.threading.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.threading.ClientConnection;

public class ThreadPerRequestServer {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPerRequestServer.class);
    private static final int PORT = 8080;

    public static void main(String[] args) {
        List<ClientConnection> clientConnections = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port {}", PORT);

            while (!serverSocket.isClosed()) {
                acceptNewConnections(serverSocket, clientConnections);
                handleRequests(clientConnections);
            }

        } catch (IOException e) {
            logger.error("Server error", e);
        } finally {
            closeClientConnection(clientConnections);
        }
    }

    private static void acceptNewConnections(ServerSocket serverSocket, List<ClientConnection> clientConnections) throws SocketException {
        serverSocket.setSoTimeout(100);
        try {
            Socket newClient = serverSocket.accept();
            ClientConnection clientConnection = new ClientConnection(newClient);
            clientConnections.add(clientConnection);
            logger.info("New client connected: {}", newClient.getInetAddress());
        } catch (IOException ignored) {
            // ignore expected socket timeout
        }
    }

    private static void handleRequests(List<ClientConnection> clientConnections) {
        Iterator<ClientConnection> iterator = clientConnections.iterator();
        while (iterator.hasNext()) {
            ClientConnection client = iterator.next();

            if (client.getSocket()
                .isClosed()) {
                logger.info("Client disconnected: {}", client.getSocket()
                    .getInetAddress());
                iterator.remove();
                continue;
            }

            try {
                BufferedReader reader = client.getReader();
                if (reader.ready()) {
                    String request = reader.readLine();
                    if (request != null) {
                        new ThreadPerRequest(client.getWriter(), request).start();
                    }
                }
            } catch (IOException e) {
                logger.error("Error reading from client {}", client.getSocket()
                    .getInetAddress(), e);
            }
        }
    }

    private static void closeClientConnection(List<ClientConnection> clientConnections) {
        for (ClientConnection client : clientConnections) {
            try {
                client.close();
            } catch (IOException e) {
                logger.error("Error closing client connection", e);
            }
        }
    }
}
