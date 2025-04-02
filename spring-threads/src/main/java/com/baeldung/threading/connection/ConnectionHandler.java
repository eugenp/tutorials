package com.baeldung.threading.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class to handle each client connection in a separate thread
 */
public class ConnectionHandler extends Thread {

    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            String request;
            while ((request = reader.readLine()) != null) {
                Thread.sleep(1000);
                System.out.println("Processing request: " + request);
                writer.println("HTTP/1.1 200 OK - Processed request: " + request);
                System.out.println("Processed request: " + request);
            }
        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Client disconnected: " + socket.getInetAddress());
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
