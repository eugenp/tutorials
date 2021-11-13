package com.baeldung.httpsclientauthentication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SSLSocketEchoServer {

    static void startServer(int port) throws IOException {

        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();

        try (SSLServerSocket listener = (SSLServerSocket) factory.createServerSocket(port)) {
            listener.setNeedClientAuth(true);
            listener.setEnabledCipherSuites(new String[] { "TLS_AES_128_GCM_SHA256" });
            listener.setEnabledProtocols(new String[] { "TLSv1.3" });
            System.out.println("listening for messages...");
            try (Socket socket = listener.accept()) {
                InputStream is = new BufferedInputStream(socket.getInputStream());
                OutputStream os = new BufferedOutputStream(socket.getOutputStream());
                byte[] data = new byte[2048];
                int len = is.read(data);
                if (len <= 0) {
                    throw new IOException("no data received");
                }
                String message = new String(data, 0, len);
                System.out.printf("server received %d bytes: %s%n", len, message);
                String response = message + " processed by server";
                os.write(response.getBytes(), 0, response.getBytes().length);
                os.flush();
            }
            System.out.println("message processed, exiting");
        }
    }

    public static void main(String[] args) throws IOException {
        startServer(8443);
    }
}