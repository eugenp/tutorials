package com.baeldung.datagramchannel;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

public class DatagramChannelBuilder {
    
    public static DatagramChannel openChannel() throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        return datagramChannel;
    }
    
    public static DatagramChannel bindChannel(SocketAddress local) throws IOException {
        return openChannel().bind(local); 
    }

}
