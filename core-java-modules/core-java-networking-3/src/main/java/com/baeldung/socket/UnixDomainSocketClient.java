package com.baeldung.socket;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;

class UnixDomainSocketClient {

    public static void main(String[] args) throws Exception {
        new UnixDomainSocketClient().runClient();
    }

    void runClient() throws IOException {
        Path socketPath = Path.of(System.getProperty("user.home"))
          .resolve("baeldung.socket");
        UnixDomainSocketAddress socketAddress = getAddress(socketPath);

        SocketChannel channel = openSocketChannel(socketAddress);

        String message = "Hello from Baeldung Unix domain socket article";
        writeMessage(channel, message);
    }

    UnixDomainSocketAddress getAddress(Path socketPath) {
        return UnixDomainSocketAddress.of(socketPath);
    }

    SocketChannel openSocketChannel(UnixDomainSocketAddress socketAddress) throws IOException {
        SocketChannel channel = SocketChannel
          .open(StandardProtocolFamily.UNIX);
        channel.connect(socketAddress);
        return channel;
    }

    void writeMessage(SocketChannel socketChannel, String message) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        buffer.put(message.getBytes());
        buffer.flip();

        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
    }

}