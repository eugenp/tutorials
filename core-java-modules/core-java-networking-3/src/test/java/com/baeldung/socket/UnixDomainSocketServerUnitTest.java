package com.baeldung.socket;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.file.Path;

import static java.nio.file.Files.deleteIfExists;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.junit.Assert.assertEquals;

public class UnixDomainSocketServerUnitTest {

    @Test
    public void givenSocketPath_shouldCreateUnixDomainSocketAddress() {
        // given
        File tempFile = newTemporaryFile();
        Path socketPath = tempFile.toPath();

        // when
        UnixDomainSocketAddress address = new UnixDomainSocketServer().getAddress(socketPath);

        // then
        assertEquals(address.getPath(), socketPath);

        // cleanup
        tempFile.delete();
    }

    @Test
    public void givenUnixDomainSocketAddress_shouldCreateServerSocketChannel() throws IOException {
        // given
        File tempFile = newTemporaryFile();
        Path socketPath = tempFile.toPath();
        deleteIfExists(socketPath);
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath);

        // when
        ServerSocketChannel serverSocketChannel = new UnixDomainSocketServer().createServerSocketChannel(address);

        // then
        assertEquals(serverSocketChannel.getLocalAddress(), address);

        // cleanup
        tempFile.delete();
    }
}
