package com.baeldung.ipaddresses;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SubnetScannerUnitTest {

    @Test
    public void givenSubnet_whenScanningForDevices_thenReturnConnectedIPs() throws Exception {
        String subnet = getSubnet();
        List<String> connectedIPs = new ArrayList<>();

        for (int i = 1; i <= 254; i++) {
            String ip = subnet + "." + i;
            if (InetAddress.getByName(ip).isReachable(100)) {
                connectedIPs.add(ip);
            }
        }

        Assumptions.assumeTrue(!connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenUsingStream_thenReturnConnectedIPs() throws UnknownHostException {
        String subnet = getSubnet();

        List<String> connectedIPs = IntStream.rangeClosed(1, 254)
                .mapToObj(i -> subnet + "." + i)
                .filter(ip -> {
                    try {
                        return InetAddress.getByName(ip).isReachable(100);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        Assumptions.assumeTrue(!connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenCheckingForOpenPorts_thenReturnDevicesWithOpenPort() throws UnknownHostException {
        String subnet = getSubnet() + ".0/24";
        SubnetUtils utils = new SubnetUtils(subnet);
        int port = 80;

        List<String> devicesWithOpenPort = Arrays.stream(utils.getInfo().getAllAddresses())
                .filter(ip -> {
                    try (Socket socket = new Socket()) {
                        socket.connect(new InetSocketAddress(ip, port), 100);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        Assumptions.assumeTrue(!devicesWithOpenPort.isEmpty());
    }

    private String getSubnet() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        byte[] ipAddr = localHost.getAddress();
        return String.format("%d.%d.%d", (ipAddr[0] & 0xFF), (ipAddr[1] & 0xFF), (ipAddr[2] & 0xFF)); // Returns only the first three octets
    }
}