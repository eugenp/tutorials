package com.baeldung.inputstream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class InputStreamTest {

    @Test
    public void givenAString_whenWrittenToFileInputStream_thenShouldMatchWhenRead(@TempDir Path tempDir) throws IOException {

        Path sampleOut = tempDir.resolve("sample-out.txt");
        List<String> lines = Arrays.asList("Hello. This is just a test. Good bye.");
        Files.write(sampleOut, lines);
        File sampleOutFile = sampleOut.toFile();
        try(InputStream inputStream = new FileInputStream(sampleOutFile)){
            Assert.assertTrue(readString(inputStream).contains(lines.get(0)));
        }
    }

    @Test
    public void givenAString_whenWrittenToByteArrayInputStream_thenShouldMatchWhenRead() throws IOException {
        byte[] byteArray = {104, 101, 108, 108,111};
        try(ByteArrayInputStream bais = new ByteArrayInputStream(byteArray)){
            int content;
            StringBuilder actualReadText = new StringBuilder();
            while ((content = bais.read()) != -1) {
                actualReadText.append((char) content);
            }
            Assert.assertEquals( "hello",actualReadText.toString());
        }

    }

    private static String readString(InputStream inputStream) throws IOException {
        String strRet = "";
        int c;
        StringBuilder sb = new StringBuilder();
        while(!((c = inputStream.read()) != -1)) {
            sb.append((char) c);
        }
        strRet = sb.toString();

        return  strRet;
    }


}
