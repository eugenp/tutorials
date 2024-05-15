package com.baeldung.array.conversions;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertArrayEquals;

public class ByteToCharArrayUnitTest {
    public static byte[] byteArray = {65, 66, 67, 68};
    public static char[] expected = {'A', 'B', 'C', 'D'};

    @Test
    public void givenByteArray_WhenUsingStandardCharsets_thenConvertToCharArray() {
        char[] charArray = new String(byteArray, StandardCharsets.UTF_8).toCharArray();
        assertArrayEquals(expected, charArray);
    }

    @Test
    public void givenByteArray_WhenUsingSUsingStreams_thenConvertToCharArray() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
        InputStreamReader reader = new InputStreamReader(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int data;
        while ((data = reader.read()) != -1) {
            char ch = (char) data;
            outputStream.write(ch);
        }
        char[] charArray = outputStream.toString().toCharArray();
        assertArrayEquals(expected, charArray);
    }

    @Test
    public void givenByteArray_WhenUsingCharBuffer_thenConvertToCharArray() {
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArray);
        CharBuffer charBuffer = StandardCharsets.UTF_8.decode(byteBuffer);
        char[] charArray = new char[charBuffer.remaining()];
        charBuffer.get(charArray);
        assertArrayEquals(expected, charArray);
    }
}
