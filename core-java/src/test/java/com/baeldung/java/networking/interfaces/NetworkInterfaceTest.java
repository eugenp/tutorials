package com.baeldung.java.networking.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;

public class NetworkInterfaceTest {
    @Test
    public void givenName_whenReturnsNetworkInterface_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertFalse(nif == null);
    }

    @Test
    public void givenInExistentName_whenReturnsNull_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("inexistent_name");
        assertTrue(nif == null);
    }

    @Test
    public void givenIP_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        byte[] ip = new byte[] { 127, 0, 0, 1 };
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getByAddress(ip));
        assertFalse(nif == null);
    }

    @Test
    public void givenHostName_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getByName("localhost"));
        assertFalse(nif == null);
    }

    @Test
    public void givenLocalHost_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        assertFalse(nif == null);
    }

    @Test
    public void givenLoopBack_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getLoopbackAddress());
        assertFalse(nif == null);
    }

    @Test
    public void givenIndex_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByIndex(0);
        assertFalse(nif == null);
    }

    @Test
    public void givenInterface_whenReturnsInetAddresses_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        Enumeration<InetAddress> addressEnum = nif.getInetAddresses();
        InetAddress address = addressEnum.nextElement();
        assertEquals("127.0.0.1", address.getHostAddress());
    }

    @Test
    public void givenInterface_whenReturnsInterfaceAddresses_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");

        List<InterfaceAddress> addressEnum = nif.getInterfaceAddresses();
        InterfaceAddress address = addressEnum.get(0);
        InetAddress localAddress = address.getAddress();
        InetAddress broadCastAddress = address.getBroadcast();
        assertEquals("127.0.0.1", localAddress.getHostAddress());
        assertEquals("127.255.255.255", broadCastAddress.getHostAddress());
    }

    @Test
    public void givenInterface_whenChecksIfLoopback_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertTrue(nif.isLoopback());
    }

    @Test
    public void givenInterface_whenChecksIfUp_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertTrue(nif.isUp());
    }

    @Test
    public void givenInterface_whenChecksIfPointToPoint_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertFalse(nif.isPointToPoint());
    }

    @Test
    public void givenInterface_whenChecksIfVirtual_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertFalse(nif.isVirtual());
    }

    @Test
    public void givenInterface_whenChecksMulticastSupport_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        assertTrue(nif.supportsMulticast());
    }

    @Test
    public void givenInterface_whenGetsMacAddress_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        byte[] bytes = nif.getHardwareAddress();
        assertFalse(bytes == null);
    }

    @Test
    public void givenInterface_whenGetsMTU_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        int mtu = nif.getMTU();
    }
}
