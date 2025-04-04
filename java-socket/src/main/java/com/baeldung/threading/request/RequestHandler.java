package com.baeldung.threading.request;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class to handle each client request in a separate thread
 */
public class RequestHandler extends Thread {

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
            Thread.sleep(1000);
            System.out.println("Processing request: " + request);
            writer.println("HTTP/1.1 200 OK - Processed request: " + request);
            System.out.println("Processed request: " + request);
        } catch (Exception e) {
            System.err.println("Error processing request: " + e.getMessage());
        }
    }
}