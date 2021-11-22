package com.baeldung.sockets;

import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

class UnixDomainSocketClient {

    public static void main(String[] args) throws Exception {
        Path socketPath = Path.of(System.getProperty("user.home"))
          .resolve("baeldung.socket");
        UnixDomainSocketAddress socketAddress = UnixDomainSocketAddress.of(socketPath);

        SocketChannel channel = SocketChannel
          .open(StandardProtocolFamily.UNIX);
        channel.connect(socketAddress);

        String message = "Hello from Baeldung Unix domain socket article";

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

}