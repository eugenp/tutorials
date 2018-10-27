package com.baeldung.ssl.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;

public class SimpleClient {
    private static void startClient(String host, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();

        try (Socket connection = factory.createSocket(host, port)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println(input.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        startClient("localhost", 1234);
    }
}
