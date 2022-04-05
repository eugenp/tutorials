package com.baeldung.stringtobytearray;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class ByteArrayToStringUnitTest {

    @Test
    public void whenStringConstructorWithDefaultCharset_thenOK() {
        final byte[] byteArrray = { 72, 101, 108, 108, 111, 32, 87, 111, 114,
                108, 100, 33 };
        String string = new String(byteArrray);
        System.out.println(string);
        
        assertNotNull(string);
    }

    @Test
    public void whenStringConstructorWithNamedCharset_thenOK()
        throws UnsupportedEncodingException {
        final String charsetName = "IBM01140";
        final byte[] byteArrray = { -56, -123, -109, -109, -106, 64, -26, -106,
                -103, -109, -124, 90 };

        String string = new String(byteArrray, charsetName);
        
        assertEquals("Hello World!", string);
    }

    @Test
    public void whenStringConstructorWithCharSet_thenOK() {
        final Charset charset = Charset.forName("UTF-8");
        final byte[] byteArrray = { 72, 101, 108, 108, 111, 32, 87, 111, 114,
                108, 100, 33 };

        String string = new String(byteArrray, charset);

        assertEquals("Hello World!", string);
    }

    @Test
    public void whenStringConstructorWithStandardCharSet_thenOK() {
        final Charset charset = StandardCharsets.UTF_16;
        
        final byte[] byteArrray = { -2, -1, 0, 72, 0, 101, 0, 108, 0, 108, 0,
                111, 0, 32, 0, 87, 0, 111, 0, 114, 0, 108, 0, 100, 0, 33 };

        String string = new String(byteArrray, charset);

        assertEquals("Hello World!", string);
    }

    @Test
    public void whenDecodeWithCharset_thenOK() {
        byte[] byteArrray = { 72, 101, 108, 108, 111, 32, -10, 111, 114, 108, -63, 33 };
        final Charset charset = StandardCharsets.US_ASCII;

        String string = charset.decode(ByteBuffer.wrap(byteArrray)).toString();
        System.out.println(string);

        assertEquals("Hello �orl�!", string);
    }

    @Test
    public void whenUsingCharsetDecoder_thenOK()
        throws CharacterCodingException {
        byte[] byteArrray = { 72, 101, 108, 108, 111, 32, -10, 111, 114, 108, -63, 33};
        CharsetDecoder decoder = StandardCharsets.US_ASCII.newDecoder();

        decoder.onMalformedInput(CodingErrorAction.REPLACE)
            .onUnmappableCharacter(CodingErrorAction.REPLACE)
            .replaceWith("?");

        String string = decoder.decode(ByteBuffer.wrap(byteArrray)).toString();

        assertEquals("Hello ?orl?!", string);
    }


}
