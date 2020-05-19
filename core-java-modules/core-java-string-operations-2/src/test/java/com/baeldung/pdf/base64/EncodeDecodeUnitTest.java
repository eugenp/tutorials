package com.baeldung.pdf.base64;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class EncodeDecodeUnitTest {

    private static final String IN_FILE = "src/test/resources/input.pdf";
    private static final String OUT_FILE = "src/test/resources/output.pdf";
    private static byte[] inFileBytes;

    @BeforeClass
    public static void fileToByteArray() throws IOException {
        inFileBytes = Files.readAllBytes(Paths.get(IN_FILE));
    }

    @Test
    public void givenJavaBase64_whenEncoded_thenDecodedOK() throws IOException {

        byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);

        byte[] decoded = java.util.Base64.getDecoder().decode(encoded);

        writeToFile(OUT_FILE, decoded);

        assertNotEquals(encoded.length, decoded.length);
        assertEquals(inFileBytes.length, decoded.length);

        assertArrayEquals(decoded, inFileBytes);

    }

    @Test
    public void givenJavaBase64_whenEncodedStream_thenDecodedStreamOK() throws IOException {

        try (OutputStream os = java.util.Base64.getEncoder().wrap(new FileOutputStream(OUT_FILE));
          FileInputStream fis = new FileInputStream(IN_FILE)) {
            byte[] bytes = new byte[1024];
            int read;
            while ((read = fis.read(bytes)) > -1) {
                os.write(bytes, 0, read);
            }
        }

        byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
        byte[] encodedOnDisk = Files.readAllBytes(Paths.get(OUT_FILE));
        assertArrayEquals(encoded, encodedOnDisk);

        byte[] decoded = java.util.Base64.getDecoder().decode(encoded);
        byte[] decodedOnDisk = java.util.Base64.getDecoder().decode(encodedOnDisk);
        assertArrayEquals(decoded, decodedOnDisk);
    }

    @Test
    public void givenApacheCommons_givenJavaBase64_whenEncoded_thenDecodedOK() throws IOException {

        byte[] encoded = org.apache.commons.codec.binary.Base64.encodeBase64(inFileBytes);

        byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(encoded);

        writeToFile(OUT_FILE, decoded);

        assertNotEquals(encoded.length, decoded.length);
        assertEquals(inFileBytes.length, decoded.length);

        assertArrayEquals(decoded, inFileBytes);
    }

    private void writeToFile(String fileName, byte[] bytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(bytes);
        fos.flush();
        fos.close();
    }
}
