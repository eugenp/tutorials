package com.baeldung.ssl.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SimpleServer {
    static void startServer(int port) throws IOException {
        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();

        try (ServerSocket listener = factory.createServerSocket(port)) {
            System.setProperty("javax.net.debug", "ssl:handshake");
            ((SSLServerSocket) listener).setNeedClientAuth(true);
            ((SSLServerSocket) listener).setEnabledCipherSuites(new String[] { "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256" });
            ((SSLServerSocket) listener).setEnabledProtocols(new String[] { "TLSv1.2" });
            while (true) {
                try (Socket socket = listener.accept()) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println("Hello World!");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.debug", "ssl:handshake");
        startServer(8443);
    }
}