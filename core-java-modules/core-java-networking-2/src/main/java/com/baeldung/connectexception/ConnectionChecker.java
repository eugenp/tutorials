package com.baeldung.connectexception;

import java.net.ConnectException;
import java.net.Socket;

public class ConnectionChecker {
    public static void main(String argv[]) throws Exception {
        String host = "localhost";
        int port = 5010;

        try {
            Socket clientSocket = new Socket(host, port);
            System.out.println("is client connected to server? " + clientSocket.isConnected());

            clientSocket.close();
        } catch (ConnectException e) {
            System.err.println("could not connect to server " + host + ":" + port + ", check host and port combination");
        }
    }
}
