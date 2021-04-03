package com.baeldung.datagramchannel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import org.junit.jupiter.api.Test;

public class DatagramChannelUnitTest {
    
    @Test
    public void whenClientSendsAndServerReceivesUDPPacket_thenCorrect() throws IOException {
        DatagramChannel server = DatagramServer.startServer();
        
        DatagramChannel client = DatagramClient.startClient();
        String msg = "Hello, this is a Baeldung's DatagramChannel based UDP client!";
        InetSocketAddress serverAddress = new InetSocketAddress("localhost", 7001);
        DatagramClient.sendMessage(client, msg, serverAddress);
        
        assertEquals("Hello, this is a Baeldung's DatagramChannel based UDP client!", DatagramServer.receiveMessage(server));
    }

}
