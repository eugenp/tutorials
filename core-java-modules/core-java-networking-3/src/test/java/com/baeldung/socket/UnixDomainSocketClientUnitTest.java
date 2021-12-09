package com.baeldung.socket;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.UUID;

import static java.nio.file.Files.deleteIfExists;
import static org.assertj.core.util.Files.newTemporaryFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UnixDomainSocketClientUnitTest {

    @Test
    public void givenSocketPath_shouldCreateUnixDomainSocketAddress() {
        // given
        File tempFile = newTemporaryFile();
        Path socketPath = tempFile.toPath();

        // when
        UnixDomainSocketAddress address = new UnixDomainSocketClient().getAddress(socketPath);

        // then
        assertEquals(address.getPath(), socketPath);

        // cleanup
        tempFile.delete();
    }

    @Test
    public void givenUnixDomainSocketAddress_shouldOpenSocketChannel() throws IOException {
        // given
        File tempFile = newTemporaryFile();
        Path socketPath = tempFile.toPath();
        deleteIfExists(socketPath);
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of(socketPath);

        // bind address as a unix domain socket
        ServerSocketChannel serverChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
        serverChannel.bind(address);

        // when
        SocketChannel socketChannel = new UnixDomainSocketClient().openSocketChannel(address);

        // then
        assertTrue(socketChannel.isOpen());
        assertEquals(socketChannel.getRemoteAddress(), address);

        // cleanup
        tempFile.delete();
    }

    @Test
    public void givenSocketChannelAndMessage_shouldWriteMessage() throws IOException {
        // given
        SocketChannel socketChannel = Mockito.mock(SocketChannel.class);
        String message = UUID.randomUUID().toString();
        Mockito.when(socketChannel.write(Mockito.any(ByteBuffer.class)))
          .thenAnswer(
            (Answer<Integer>) invocationOnMock -> {
              ((ByteBuffer) invocationOnMock.getArguments()[0]).position(message.getBytes().length);
              return -1;
            }
          );

        // when
        new UnixDomainSocketClient().writeMessage(socketChannel, message);

        // then
        Mockito.verify(socketChannel, Mockito.times(1)).write(Mockito.any(ByteBuffer.class));
    }
}
