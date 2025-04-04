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

public class ThreadPerRequestServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        List<Socket> clientSockets = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            while (!serverSocket.isClosed()) {
                acceptNewConnections(serverSocket, clientSockets);

                Iterator<Socket> iterator = clientSockets.iterator();
                while (iterator.hasNext()) {
                    Socket clientSocket = iterator.next();
                    if (clientSocket.isClosed()) {
                        System.out.println("Client disconnected: " + clientSocket.getInetAddress());
                        iterator.remove();
                        continue;
                    }
                    createRequestHandler(clientSocket);
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            closeClientSockets(clientSockets);
        }
    }

    private static void acceptNewConnections(ServerSocket serverSocket, List<Socket> clientSockets) throws SocketException {
        // Small timeout for non-blocking accept
        serverSocket.setSoTimeout(100);
        try {
            Socket newClient = serverSocket.accept();
            clientSockets.add(newClient);
            System.out.println("New client connected: " + newClient.getInetAddress());
        } catch (IOException ignored) {
            // No new connection, continue to process existing ones
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
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}