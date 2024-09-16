package com.baeldung.urlipaddress;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class URLIPAddress {

    public String getByInetAddress(String urlString) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName(urlString);
        return ip.getHostAddress();
    }

    public String getBySocketConnection(String urlString) throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(urlString, 80));
            return socket.getLocalAddress()
                .getHostAddress();
        }
    }
}