package com.baeldung.networking.mac.address;

import org.junit.Test;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertNotNull;

public class MacAddressUnitTest {

    @Test
    public void givenNetworkInterface_whenUsingLocalHost_thenGetMacAddress() throws UnknownHostException, SocketException {
        InetAddress localHost = InetAddress.getLocalHost();
        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        byte[] macAddress = ni.getHardwareAddress();
        assertNotNull(macAddress);
    }

    @Test
    public void givenNetworkInterface_whenIPSpecified_thenGetMacAddress() throws SocketException, UnknownHostException {
        InetAddress localIP = InetAddress.getByName("192.168.1.108");
        NetworkInterface ni = NetworkInterface.getByInetAddress(localIP);
        byte[] macAddress = ni.getHardwareAddress();
        assertNotNull(macAddress);
    }

}
