package com.baeldung.iplookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IPAddressLookup {

    public static void main(String[] args) {
        System.out.println("UDP connection IP lookup: " + getLocalIpAddressUdp());
        System.out.println("Socket connection IP lookup: " + getLocalIpAddressSocket());
        System.out.println("AWS connection IP lookup: " + getPublicIpAddressAws());
        System.out.println("Local connection IP lookup: " + getLocalIpAddress());
        System.out.println("Local connection IP lookup, multiple addresses: " + getAllLocalIpAddressUsingNetworkInterface());
    }

    public static String getLocalIpAddressUdp() {
        try (final DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 12345);
            return datagramSocket.getLocalAddress()
                .getHostAddress();
        } catch (SocketException | UnknownHostException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static String getLocalIpAddressSocket() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            return socket.getLocalAddress()
                .getHostAddress();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPublicIpAddressAws() {
        try {
            String urlString = "http://checkip.amazonaws.com/";
            URL url = new URI(urlString).toURL();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
                return br.readLine();
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getLocalIpAddress() {
        try {
            return Inet4Address.getLocalHost()
                .getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getAllLocalIpAddressUsingNetworkInterface() {
        List<String> ipAddress = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaceEnumeration = null;
        try {
            networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        for (; networkInterfaceEnumeration.hasMoreElements(); ) {
            NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
            try {
                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }

            Enumeration<InetAddress> address = networkInterface.getInetAddresses();
            for (; address.hasMoreElements(); ) {
                InetAddress addr = address.nextElement();
                ipAddress.add(addr.getHostAddress());
            }
        }
        return ipAddress;
    }

}
