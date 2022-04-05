package com.baeldung.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;

public class GreetServer {

    private static final Logger LOG = LoggerFactory.getLogger(GreetServer.class);

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            if ("hello server".equals(greeting))
                out.println("hello client");
            else
                out.println("unrecognised greeting");
        } catch (IOException e) {
            LOG.debug(e.getMessage());
        }

    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            LOG.debug(e.getMessage());
        }

    }

    public static void main(String[] args) {
        GreetServer server = new GreetServer();
        server.start(6666);
    }

}
