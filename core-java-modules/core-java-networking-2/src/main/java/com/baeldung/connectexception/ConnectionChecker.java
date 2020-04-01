package com.baeldung.connectexception;

import java.net.ConnectException;
import java.net.Socket;

public class ConnectionChecker {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try {
            Socket clientSocket = new Socket(host, port);

            // successfully connected to host, do something with opened socket

            clientSocket.close();
        } catch (ConnectException e) {
            // host and port combination not valid
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
