package com.baeldung.datagramchannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramClient {
    
    public static DatagramChannel startClient() throws IOException {
        DatagramChannel client = DatagramChannelBuilder.bindChannel(null);
        client.configureBlocking(false);
        return client;
    }
    
    public static void sendMessage(DatagramChannel client, String msg, SocketAddress serverAddress) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        client.send(buffer, serverAddress);
    }

    public static void main(String[] args) throws IOException {
        DatagramChannel client = startClient();
        String msg = "Hello, this is a Baeldung's DatagramChannel based UDP client!";
        InetSocketAddress serverAddress = new InetSocketAddress("localhost", 7001);
        
        sendMessage(client, msg, serverAddress);
        
    }

}
