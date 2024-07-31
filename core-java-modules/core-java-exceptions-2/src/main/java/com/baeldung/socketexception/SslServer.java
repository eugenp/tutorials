package com.baeldung.socketexception;

import java.io.IOException;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class SslServer {

    public static void createSSLSocketWithEnabledProtocols(SSLServerSocketFactory socketFactory, int port, String[] enabledProtocols) {
        SSLServerSocket serverSocket = null;

        try {
            serverSocket = (SSLServerSocket) socketFactory.createServerSocket(port);
            // Set the enabled protocols
            serverSocket.setEnabledProtocols(enabledProtocols);
            System.out.println("Server is running on port " + port);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
