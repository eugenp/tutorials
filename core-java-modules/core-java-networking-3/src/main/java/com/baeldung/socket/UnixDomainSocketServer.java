package com.baeldung.socket;

import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

class UnixDomainSocketServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        new UnixDomainSocketServer().runServer();
    }

    void runServer() throws IOException, InterruptedException {
        Path socketPath = Path.of(System.getProperty("user.home"))
          .resolve("baeldung.socket");
        Files.deleteIfExists(socketPath);
        UnixDomainSocketAddress socketAddress = getAddress(socketPath);

        ServerSocketChannel serverChannel = createServerSocketChannel(socketAddress);

        SocketChannel channel = serverChannel.accept();

        while (true) {
            readSocketMessage(channel)
              .ifPresent(message -> System.out.printf("[Client message] %s%n", message));
            Thread.sleep(100);
        }
    }

    UnixDomainSocketAddress getAddress(Path socketPath) {
        return UnixDomainSocketAddress.of(socketPath);
    }

    ServerSocketChannel createServerSocketChannel(UnixDomainSocketAddress socketAddress) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
        serverChannel.bind(socketAddress);
        return serverChannel;
    }

    Optional<String> readSocketMessage(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        if (bytesRead < 0) return Optional.empty();
        byte[] bytes = new byte[bytesRead];
        buffer.flip();
        buffer.get(bytes);
        String message = new String(bytes);
        return Optional.of(message);
    }

}