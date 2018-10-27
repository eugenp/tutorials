package com.baeldung.ssl.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SimpleClient {
    static void startClient(String host, int port) throws IOException {
        SocketFactory factory = SSLSocketFactory.getDefault();
        try (Socket connection = factory.createSocket(host, port)) {
            ((SSLSocket) connection).setEnabledCipherSuites(
              new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256"});
            ((SSLSocket) connection).setEnabledProtocols(
              new String[] { "TLSv1.2"});
            BufferedReader input = new BufferedReader(
              new InputStreamReader(connection.getInputStream()));
            System.out.println(input.readLine());
        }
    }

    public static void main(String[] args) throws IOException {
        startClient("localhost", 1234);
    }
}
