package com.baeldung.java.networking.udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastEchoServer extends Thread {

    protected MulticastSocket socket = null;
    protected boolean running;
    protected byte[] buf = new byte[256];

    public MulticastEchoServer() throws IOException {
        socket = new MulticastSocket(4446);
        socket.setReuseAddress(true);
        InetAddress group = InetAddress.getByName("224.0.0.0");
        socket.joinGroup(group);
    }

    public void run() {
        running = true;

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                String received = new String(packet.getData(), 0, packet.getLength());
                if (received.equals("end")) {
                    running = false;
                    continue;
                }
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }
        }
        socket.close();
    }
}
