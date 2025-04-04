package com.baeldung.threading.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadPerRequestServer {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPerRequestServer.class);

    private static final int PORT = 8080;

    public static void main(String[] args) {
        List<Socket> clientSockets = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Server started on port {}", PORT);

            while (!serverSocket.isClosed()) {
                acceptNewConnections(serverSocket, clientSockets);

                Iterator<Socket> iterator = clientSockets.iterator();
                while (iterator.hasNext()) {
                    Socket clientSocket = iterator.next();
                    if (clientSocket.isClosed()) {
                        logger.info("Client disconnected: {}", clientSocket.getInetAddress());
                        iterator.remove();
                        continue;
                    }
                    createRequestHandler(clientSocket);
                }
            }
        } catch (IOException e) {
            logger.error("Server error: {}", e.getMessage());
        } finally {
            closeClientSockets(clientSockets);
        }
    }

    private static void acceptNewConnections(ServerSocket serverSocket, List<Socket> clientSockets) throws SocketException {
        serverSocket.setSoTimeout(100);
        try {
            Socket newClient = serverSocket.accept();
            clientSockets.add(newClient);
            logger.info("New client connected: {}", newClient.getInetAddress());
        } catch (IOException ignored) {
            // ignored
        }
    }

    private static void createRequestHandler(Socket clientSocket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String request;
        if ((reader.ready()) && (request = reader.readLine()) != null) {
            new RequestHandler(clientSocket, request).start();
        }
    }

    private static void closeClientSockets(List<Socket> clientSockets) {
        for (Socket socket : clientSockets) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                logger.error("Error closing client socket: {}", e.getMessage());
            }
        }
    }
}