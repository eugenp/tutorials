package com.baeldung.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGZip {

    Logger logger = LoggerFactory.getLogger(TestGZip.class);

    @Test
    public void testGzip() throws IOException {
        String payload = "This is a sample text to test methods gzip and gunzip. Have a nice day!";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        GZip.gzip(new ByteArrayInputStream(payload.getBytes()), os);
        byte[] compressed = os.toByteArray();
        ByteArrayOutputStream ungzipOS = new ByteArrayOutputStream();
        GZip.gunzip(new ByteArrayInputStream(compressed), ungzipOS);
        logger.debug(String.format("decompressed text: %s", new String(ungzipOS.toByteArray())));
    }

}
