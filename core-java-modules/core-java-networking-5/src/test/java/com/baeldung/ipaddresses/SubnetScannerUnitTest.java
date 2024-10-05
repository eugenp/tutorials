package com.baeldung.ipaddresses;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubnetScannerUnitTest {
    String subnet = "192.168.1";

    @Test
    public void givenSubnet_whenScanningForDevices_thenReturnConnectedIPs() throws Exception {
        List<String> connectedIPs = new ArrayList<>();

        for (int i = 1; i <= 254; i++) {
            String ip = subnet + "." + i;
            if (InetAddress.getByName(ip).isReachable(1000)) {
                connectedIPs.add(ip);
            }
        }

        assertFalse(connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenUsingStream_thenReturnConnectedIPs() {
        List<String> connectedIPs = IntStream.rangeClosed(1, 254)
                .mapToObj(i -> subnet + "." + i)
                .filter(ip -> {
                    try {
                        return InetAddress.getByName(ip).isReachable(1000);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        assertFalse(connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenUsingApacheCommonsNet_thenReturnConnectedIPs() {
        SubnetUtils utils = new SubnetUtils(subnet + "/24");
        List<String> connectedIPs = Arrays.stream(utils.getInfo().getAllAddresses())
                .filter(ip -> {
                    try {
                        return InetAddress.getByName(ip).isReachable(1000);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        assertFalse(connectedIPs.isEmpty());
    }
}