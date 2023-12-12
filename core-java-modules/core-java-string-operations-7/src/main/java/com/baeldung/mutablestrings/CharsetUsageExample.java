package com.baeldung.mutablestrings;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

public class CharsetUsageExample {

    public ByteBuffer encodeString(String inputString) {
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = CharBuffer.wrap(inputString);
        ByteBuffer byteBuffer = ByteBuffer.allocate(50);

        encoder.encode(charBuffer, byteBuffer, true); // true indicates the end of input
        byteBuffer.flip();
        return byteBuffer;
    }

    public String decodeString(ByteBuffer byteBuffer) {
        Charset charset = Charset.forName("UTF-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer decodedCharBuffer = CharBuffer.allocate(50);
        decoder.decode(byteBuffer, decodedCharBuffer, true);
        decodedCharBuffer.flip();

        return decodedCharBuffer.toString();
    }
}
