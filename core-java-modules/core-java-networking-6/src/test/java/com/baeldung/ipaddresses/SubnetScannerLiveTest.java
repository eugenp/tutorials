package com.baeldung.ipaddresses;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Test;

public class SubnetScannerLiveTest {

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

        assertFalse(connectedIPs.isEmpty());
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

        assertFalse(connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenCheckingForOpenPorts_thenReturnDevicesWithOpenPort() throws UnknownHostException {
        SubnetUtils utils = new SubnetUtils(getSubnet() + ".0/24");
        int port = 80;
        List<String> devicesWithOpenPort = Arrays.stream(utils.getInfo().getAllAddresses())
                .filter(ip -> {
                    TelnetClient telnetClient = new TelnetClient();
                    try {
                        telnetClient.setConnectTimeout(100);
                        telnetClient.connect(ip, port);
                        return telnetClient.isConnected();
                    } catch (Exception e) {
                        return false;
                    } finally {
                        try {
                            if (telnetClient.isConnected()) {
                                telnetClient.disconnect();
                            }
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                })
                .toList();

        assertFalse(devicesWithOpenPort.isEmpty());
    }

    private String getSubnet() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        byte[] ipAddr = localHost.getAddress();
        return String.format("%d.%d.%d", (ipAddr[0] & 0xFF), (ipAddr[1] & 0xFF), (ipAddr[2] & 0xFF));
    }
}
