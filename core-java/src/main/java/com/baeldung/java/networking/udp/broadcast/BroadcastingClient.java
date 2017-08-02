package com.baeldung.java.networking.udp.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

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
        broadcastPacket();
        
        return receivePackets();
    }

    private void initializeSocketForBroadcasting() throws SocketException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }
    
    private void copyMessageOnBuffer(String msg) {
        buf = msg.getBytes();
    }
    
    private void broadcastPacket() throws IOException {
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
