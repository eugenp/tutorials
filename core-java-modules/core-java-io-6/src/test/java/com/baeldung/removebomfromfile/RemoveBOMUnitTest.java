package com.baeldung.removebomfromfile;

import org.junit.Test;

import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RemoveBOMUnitTest {
    String filePath;
    String expectedContent = "This is a test file with a UTF-8 BOM.";

    {
        try {
            filePath = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("file.txt")).toURI()).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenFileWithBOM_whenUsingInputStreamAndReader_thenRemoveBOM() throws IOException {
        try (InputStream is = new FileInputStream(filePath)) {
            byte[] bom = new byte[3];
            int n = is.read(bom, 0, bom.length);

            Reader reader;
            if (n == 3 && (bom[0] & 0xFF) == 0xEF && (bom[1] & 0xFF) == 0xBB && (bom[2] & 0xFF) == 0xBF) {
                reader = new InputStreamReader(is, StandardCharsets.UTF_8);
            } else {
                reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            }
            assertEquals(expectedContent, readFully(reader));
        }
    }

    @Test
    public void givenFileWithBOM_whenUsingApacheCommonsIO_thenRemoveBOM() throws IOException {
        try (BOMInputStream bomInputStream = new BOMInputStream(new FileInputStream(filePath));
             Reader reader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8)) {

            assertTrue(bomInputStream.hasBOM());

            assertEquals(expectedContent, readFully(reader));
        }
    }

    @Test
    public void givenFileWithBOM_whenUsingNIO_thenRemoveBOM() throws IOException, URISyntaxException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        ByteBuffer buffer = ByteBuffer.wrap(fileBytes);

        if (buffer.remaining() >= 3) {
            byte b0 = buffer.get();
            byte b1 = buffer.get();
            byte b2 = buffer.get();

            if ((b0 & 0xFF) == 0xEF && (b1 & 0xFF) == 0xBB && (b2 & 0xFF) == 0xBF) {
                assertEquals(expectedContent, StandardCharsets.UTF_8.decode(buffer).toString());
            } else {
                buffer.position(0);
                assertEquals(expectedContent, StandardCharsets.UTF_8.decode(buffer).toString());
            }
        } else {
            assertEquals(expectedContent, StandardCharsets.UTF_8.decode(buffer).toString());
        }
    }

    private String readFully(Reader reader) throws IOException {
        StringBuilder content = new StringBuilder();
        char[] buffer = new char[1024];
        int numRead;
        while ((numRead = reader.read(buffer)) != -1) {
            content.append(buffer, 0, numRead);
        }
        return content.toString();
    }
}
