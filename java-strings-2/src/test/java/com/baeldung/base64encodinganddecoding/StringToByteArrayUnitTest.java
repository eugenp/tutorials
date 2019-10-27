package com.baeldung.base64encodinganddecoding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

public class StringToByteArrayUnitTest {

    @Test
    public void whenConvertStringToByteArrayUsingStringClass_thenOk() {
        final String originalInput = "test input";
        byte[] result = originalInput.getBytes();
        System.out.println(Arrays.toString(result));
        
        assertEquals(originalInput.length(), result.length);
    }

    @Test
    public void givenCharset_whenConvertStringToByteArrayUsingStringClass_thenOk() throws UnsupportedEncodingException {
        final String originalInput = "test input";
        byte[] result = originalInput.getBytes(StandardCharsets.UTF_16);
        System.out.println(Arrays.toString(result));
        
        assertTrue(originalInput.length() < result.length);
    }
    
    @Test
    public void whenConvertStringToByteArrayUsingBase64Decoder_thenOk() {
        final String originalInput = "dGVzdCBpbnB1dA==";
        byte[] result = Base64.getDecoder().decode(originalInput);
        
        assertEquals("test input", new String(result));
    }
    
    @Test
    public void whenConvertStringToByteArrayUsingDatatypeConverter_thenOk() {
        final String originalInput = "dGVzdCBpbnB1dA==";
        byte[] result = DatatypeConverter.parseBase64Binary(originalInput);
        
        assertEquals("test input", new String(result));
    }    
    
    @Test
    public void whenConvertStringToByteArray_thenOk(){
        String originalInput = "7465737420696E707574";
        byte[] result = DatatypeConverter.parseHexBinary(originalInput);
        System.out.println(Arrays.toString(result));
        
        assertEquals("test input", new String(result));
    }
}
