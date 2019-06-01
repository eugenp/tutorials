package com.baeldung.string.conversion;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

public class StringToByteArrayUnitTest {

    @Test
    public void whenGetBytesWithDefaultCharset_thenOK() {
        final String inputString = "Hello World!";
        final String defaultCharSet = Charset.defaultCharset()
            .displayName();

        byte[] byteArrray = inputString.getBytes();

        System.out.printf(
            "Using default charset:%s, Input String:%s, Output byte array:%s\n",
            defaultCharSet, inputString, Arrays.toString(byteArrray));

        assertNotNull(byteArrray);
        assert (byteArrray.length >= inputString.length());
    }

    @Test
    public void whenGetBytesWithNamedCharset_thenOK()
        throws UnsupportedEncodingException {
        final String inputString = "Hello World!";
        final String charsetName = "IBM01140";

        byte[] byteArrray = inputString.getBytes("IBM01140");

        System.out.printf(
            "Using named charset:%s, Input String:%s, Output byte array:%s\n",
            charsetName, inputString, Arrays.toString(byteArrray));

        assertArrayEquals(new byte[] { -56, -123, -109, -109, -106, 64, -26,
                -106, -103, -109, -124, 90 },
            byteArrray);
    }

    @Test
    public void whenGetBytesWithCharset_thenOK() {
        final String inputString = "Hello ਸੰਸਾਰ!";
        final Charset charset = Charset.forName("ASCII");

        byte[] byteArrray = inputString.getBytes(charset);

        System.out.printf(
            "Using Charset:%s, Input String:%s, Output byte array:%s\n",
            charset, inputString, Arrays.toString(byteArrray));

        assertArrayEquals(
            new byte[] { 72, 101, 108, 108, 111, 32, 63, 63, 63, 63, 63, 33 },
            byteArrray);
    }

    @Test
    public void whenGetBytesWithStandardCharset_thenOK() {
        final String inputString = "Hello World!";
        final Charset charset = StandardCharsets.UTF_16;

        byte[] byteArrray = inputString.getBytes(charset);

        System.out.printf(
            "Using Standard Charset:%s, Input String:%s, Output byte array:%s\n",
            charset, inputString, Arrays.toString(byteArrray));

        assertArrayEquals(new byte[] { -2, -1, 0, 72, 0, 101, 0, 108, 0, 108, 0,
                111, 0, 32, 0, 87, 0, 111, 0, 114, 0, 108, 0, 100, 0, 33 },
            byteArrray);
    }

    @Test
    public void whenEncodeWithCharset_thenOK() {
        final String inputString = "Hello ਸੰਸਾਰ!";
        final Charset charset = StandardCharsets.US_ASCII;

        byte[] byteArrray = charset.encode(inputString)
            .array();

        System.out.printf(
            "Using encode with Charset:%s, Input String:%s, Output byte array:%s\n",
            charset, inputString, Arrays.toString(byteArrray));

        assertArrayEquals(
            new byte[] { 72, 101, 108, 108, 111, 32, 63, 63, 63, 63, 63, 33 },
            byteArrray);
    }

    @Test
    public void whenUsingCharsetEncoder_thenOK()
        throws CharacterCodingException {
        final String inputString = "Hello ਸੰਸਾਰ!";
        CharsetEncoder encoder = StandardCharsets.US_ASCII.newEncoder();
        encoder.onMalformedInput(CodingErrorAction.IGNORE)
            .onUnmappableCharacter(CodingErrorAction.REPLACE)
            .replaceWith(new byte[] { 0 });

        byte[] byteArrray = encoder.encode(CharBuffer.wrap(inputString))
            .array();

        System.out.printf(
            "Using encode with CharsetEncoder:%s, Input String:%s, Output byte array:%s\n",
            encoder, inputString, Arrays.toString(byteArrray));

        assertArrayEquals(
            new byte[] { 72, 101, 108, 108, 111, 32, 0, 0, 0, 0, 0, 33 },
            byteArrray);
    }

}
