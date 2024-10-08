package com.baeldung.ipaddresses;

import org.apache.commons.net.util.SubnetUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SubnetScannerUnitTest {
    String subnet = "192.168.1.0";

    @Test
    public void givenSubnet_whenScanningForDevices_thenReturnConnectedIPs() throws Exception {
        List<String> connectedIPs = new ArrayList<>();

        for (int i = 1; i <= 254; i++) {
            String ip = subnet.substring(0, subnet.lastIndexOf('.')) + "." + i;
            if (InetAddress.getByName(ip).isReachable(2000)) {
                connectedIPs.add(ip);
            }
        }

        Assumptions.assumeTrue(!connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenUsingStream_thenReturnConnectedIPs() {
        List<String> connectedIPs = IntStream.rangeClosed(1, 254)
                .mapToObj(i -> subnet.substring(0, subnet.lastIndexOf('.')) + "." + i)
                .filter(ip -> {
                    try {
                        return InetAddress.getByName(ip).isReachable(2000);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        Assumptions.assumeTrue(!connectedIPs.isEmpty());
    }

    @Test
    public void givenSubnet_whenUsingApacheCommonsNet_thenReturnConnectedIPs() {
        SubnetUtils utils = new SubnetUtils("192.168.1.0/24");
        List<String> connectedIPs = Arrays.stream(utils.getInfo().getAllAddresses())
                .filter(ip -> {
                    try {
                        return InetAddress.getByName(ip).isReachable(2000);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        Assumptions.assumeTrue(!connectedIPs.isEmpty());
    }
}
