package com.baeldung.iplookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class IPAddressLookup {
    public static void main(String[] args) {
        System.out.println("UDP connection IP lookup: " + getLocalIpAddressUdp());
        System.out.println("Socket connection IP lookup: " + getLocalIpAddressSocket());
        System.out.println("AWS connection IP lookup: " + getPublicIpAddressAws());
    }

    public static String getLocalIpAddressUdp() {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
            return datagramSocket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getLocalIpAddressSocket() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            return socket.getLocalAddress().getHostAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPublicIpAddressAws() {
        try {
            String urlString = "http://checkip.amazonaws.com/";
            URL url = new URL(urlString);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                return br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
