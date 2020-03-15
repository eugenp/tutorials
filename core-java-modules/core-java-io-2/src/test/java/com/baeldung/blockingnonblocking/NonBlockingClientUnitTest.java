package com.baeldung.blockingnonblocking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertTrue;

public class NonBlockingClientUnitTest {
    private String REQUESTED_RESOURCE = "/test.json";

    @Rule public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    @Before
    public void setup() {
        stubFor(get(urlEqualTo(REQUESTED_RESOURCE)).willReturn(aResponse()
          .withStatus(200)
          .withBody("{ \"response\" : \"It worked!\" }")));
    }

    @Test
    public void givenJavaNIOSocketChannel_whenReadingAndWritingWithBuffers_thenSuccess() throws IOException {
        // given a NIO SocketChannel and a charset
        InetSocketAddress address = new InetSocketAddress("localhost", wireMockRule.port());
        SocketChannel socketChannel = SocketChannel.open(address);
        Charset charset = StandardCharsets.UTF_8;

        // when we write and read using buffers
        socketChannel.write(charset.encode(CharBuffer.wrap("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n")));

        ByteBuffer buffer = ByteBuffer.allocate(8); // or allocateDirect if we need direct memory access
        CharBuffer charBuffer = CharBuffer.allocate(8192);
        CharsetDecoder decoder = charset.newDecoder();
        StringBuilder ourStore = new StringBuilder();
        while (socketChannel.read(buffer) != -1 || buffer.position() > 0) {
            buffer.flip();
            storeBufferContents(buffer, charBuffer, decoder, ourStore);
            buffer.compact();
        }
        socketChannel.close();

        // then we read and saved our data
        assertTrue(ourStore
          .toString()
          .contains("It worked!"));
    }

    @Test
    public void givenJavaNIOSocketChannel_whenReadingAndWritingWithSmallBuffers_thenSuccess() throws IOException {
        // given a NIO SocketChannel and a charset
        InetSocketAddress address = new InetSocketAddress("localhost", wireMockRule.port());
        SocketChannel socketChannel = SocketChannel.open(address);
        Charset charset = StandardCharsets.UTF_8;

        // when we write and read using buffers that are too small for our message
        socketChannel.write(charset.encode(CharBuffer.wrap("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n")));

        ByteBuffer buffer = ByteBuffer.allocate(8); // or allocateDirect if we need direct memory access
        CharBuffer charBuffer = CharBuffer.allocate(8);
        CharsetDecoder decoder = charset.newDecoder();
        StringBuilder ourStore = new StringBuilder();
        while (socketChannel.read(buffer) != -1 || buffer.position() > 0) {
            buffer.flip();
            storeBufferContents(buffer, charBuffer, decoder, ourStore);
            buffer.compact();
        }
        socketChannel.close();

        // then we read and saved our data
        assertTrue(ourStore
          .toString()
          .contains("It worked!"));
    }

    void storeBufferContents(ByteBuffer buffer, CharBuffer charBuffer, CharsetDecoder decoder, StringBuilder ourStore) {
        decoder.decode(buffer, charBuffer, true);
        charBuffer.flip();
        ourStore.append(charBuffer);
        charBuffer.clear();
    }

}