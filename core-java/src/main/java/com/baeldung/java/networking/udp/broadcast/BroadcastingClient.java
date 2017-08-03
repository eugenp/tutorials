package com.baeldung.java.networking.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class BroadcastingClient {
    private DatagramSocket socket;
    private InetAddress    address;
    private int            expectedServerCount;
    private byte[]         buf;

    public BroadcastingClient(int expectedServerCount) throws Exception {
        this.expectedServerCount = expectedServerCount;
        this.address             = InetAddress.getByName("255.255.255.255");
    }

    public int discoverServers(String msg) throws IOException {
        initializeSocketForBroadcasting();
        copyMessageOnBuffer(msg);
        
        //When we want to broadcast not just to local network, call listAllBroadcastAddresses() and execute broadcastPacket for each value.
        broadcastPacket(address);
        
        return receivePackets();
    }

    List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while(interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            
            if(networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            
            for(InterfaceAddress address: networkInterface.getInterfaceAddresses()) {
                if(address.getBroadcast() != null) {
                    broadcastList.add(address.getBroadcast());
                }
            }
        }
        return broadcastList;
    }
    
    private void initializeSocketForBroadcasting() throws SocketException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }
    
    private void copyMessageOnBuffer(String msg) {
        buf = msg.getBytes();
    }
    
    private void broadcastPacket(InetAddress address) throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
    }
    
    private int receivePackets() throws IOException {
        int serversDiscovered = 0;
        while(serversDiscovered!=expectedServerCount) {
            receivePacket();
            serversDiscovered++;
        }
        return serversDiscovered;
    }
    
    private void receivePacket() throws IOException {
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
    }
    
    public void close() {
        socket.close();
    }
}
