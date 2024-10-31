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

    public boolean validate(final String ip) {
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

        return ip.matches(PATTERN);
    }
}
