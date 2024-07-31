package com.baeldung.encoding;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public class CharacterEncodingExamples {

    static String readFile(String filePath, String encoding) throws IOException {
        File file = new File(filePath);
        StringBuffer buffer = new StringBuffer();
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(file), encoding)) {
            int data;
            while ((data = isr.read()) != -1) {
                buffer.append((char) data);
            }
        }
        return buffer.toString();
    }

    static String convertToBinary(String input, String encoding) throws UnsupportedEncodingException {
        byte[] bytes = input.getBytes(encoding);
        StringBuffer buffer = new StringBuffer();
        for (int b : bytes) {
            buffer.append(Integer.toBinaryString((b + 256) % 256));
            buffer.append(" ");
        }
        return buffer.toString();
    }

    static String decodeText(String input, Charset charset, CodingErrorAction codingErrorAction) throws IOException {
        CharsetDecoder charsetDecoder = charset.newDecoder();
        charsetDecoder.onMalformedInput(codingErrorAction);
        return new BufferedReader(
          new InputStreamReader(
            new ByteArrayInputStream(input.getBytes(charset)), charsetDecoder)).readLine();
    }
}
