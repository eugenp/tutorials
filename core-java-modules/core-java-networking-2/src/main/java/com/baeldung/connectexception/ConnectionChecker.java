package com.baeldung.connectexception;

import java.net.ConnectException;
import java.net.Socket;

public class ConnectionChecker {
    public static void main(String argv[]) throws Exception {
        String host = "localhost";
        int port = 5010;

        try {
            Socket clientSocket = new Socket(host, port);

            //successfully connected to host, do something with opened socket

            clientSocket.close();
        } catch (ConnectException e) {
            //host and port combination not valid
        }
    }
}
