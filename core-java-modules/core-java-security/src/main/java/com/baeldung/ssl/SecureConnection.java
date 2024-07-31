package com.baeldung.ssl;

import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SecureConnection {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Use: SecureConnection host port");
            System.exit(1);
        }
        try {
            String host = getHost(args);
            Integer port = getPort(args);
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket(host, port);
            InputStream in = sslsocket.getInputStream();
            OutputStream out = sslsocket.getOutputStream();

            out.write(1);

            while (in.available() > 0) {
                System.out.print(in.read());
            }

            System.out.println("Secured connection performed successfully");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Get the host from arguments
     * @param args the arguments
     * @return the host
     */
    private static String getHost(String[] args) {
        return args[0];
    }

    /**
     * Get the port from arguments
     * @param args the arguments
     * @return the port
     */
    private static Integer getPort(String[] args) {
        return Integer.parseInt(args[1]);
    }
}