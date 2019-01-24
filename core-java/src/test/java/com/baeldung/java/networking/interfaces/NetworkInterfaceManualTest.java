package com.baeldung.java.networking.interfaces;

import org.junit.Test;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 测试：网络接口手册
 */
public class NetworkInterfaceManualTest {

    @Test
    public void givenName_whenReturnsNetworkInterface_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        assertNotNull(nif);
    }

    @Test
    public void givenInExistentName_whenReturnsNull_thenCorrect() throws SocketException {
        NetworkInterface nif = NetworkInterface.getByName("inexistent_name");
        System.out.println("nif:{}" + nif);
        assertNull(nif);
    }

    @Test
    public void givenIP_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        byte[] ip = new byte[] { 127, 0, 0, 1 };
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getByAddress(ip));
        System.out.println("nif:{}" + nif);
        assertNotNull(nif);
    }


    @Test
    public void givenHostName_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getByName("localhost"));
        System.out.println("nif:{}" + nif);
        assertNotNull(nif);
    }

    @Test
    public void givenLocalHost_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        System.out.println("nif:{}" + nif);
        assertNotNull(nif);
    }

    @Test
    public void givenLoopBack_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByInetAddress(InetAddress.getLoopbackAddress());
        System.out.println("nif:{}" + nif);
        assertNotNull(nif);
    }

    @Test
    public void givenIndex_whenReturnsNetworkInterface_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByIndex(0);
        System.out.println("nif:{}" + nif);
        assertNotNull(nif);
    }

    @Test
    public void givenInterface_whenReturnsInetAddresses_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        Enumeration<InetAddress> addressEnum = nif.getInetAddresses();
        InetAddress address = addressEnum.nextElement();
        System.out.println("address InetAddress:{}" + address.getHostAddress());
        assertEquals("127.0.0.1", address.getHostAddress());
    }

    @Test
    public void givenInterface_whenReturnsInterfaceAddresses_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);

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
        System.out.println("nif:{}" + nif);
        assertTrue(nif.isLoopback());
    }

    @Test
    public void givenInterface_whenChecksIfUp_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        assertTrue(nif.isUp());
    }

    @Test
    public void givenInterface_whenChecksIfPointToPoint_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        assertFalse(nif.isPointToPoint());
    }

    @Test
    public void givenInterface_whenChecksIfVirtual_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        assertFalse(nif.isVirtual());
    }

    @Test
    public void givenInterface_whenChecksMulticastSupport_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        assertTrue(nif.supportsMulticast());
    }

    @Test
    public void givenInterface_whenGetsMacAddress_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("lo");
        System.out.println("nif:{}" + nif);
        byte[] bytes = nif.getHardwareAddress();
        assertNotNull(bytes);
    }

    @Test
    public void givenInterface_whenGetsMTU_thenCorrect() throws SocketException, UnknownHostException {
        NetworkInterface nif = NetworkInterface.getByName("net0");
        System.out.println("nif:{}" + nif);
        int mtu = nif.getMTU();
        assertEquals(1500, mtu);
    }
}
