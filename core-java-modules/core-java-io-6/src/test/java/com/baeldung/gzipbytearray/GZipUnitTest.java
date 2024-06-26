package com.baeldung.gzipbytearray;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GZipUnitTest {

    Logger logger = LoggerFactory.getLogger(GZipUnitTest.class);

    @Test
    void whenCompressingUsingGZip_thenGetCompressedByteArray() throws IOException {
        String payload = "This is a sample text to test method gzip. The gzip algorithm will compress this string. "
            + "The result will be smaller than this string.";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        GZip.gzip(new ByteArrayInputStream(payload.getBytes()), os);
        byte[] compressed = os.toByteArray();
        assertTrue(payload.getBytes().length > compressed.length);
        assertEquals("1f", Integer.toHexString(compressed[0] & 0xFF));
        assertEquals("8b", Integer.toHexString(compressed[1] & 0xFF));
    }

}
