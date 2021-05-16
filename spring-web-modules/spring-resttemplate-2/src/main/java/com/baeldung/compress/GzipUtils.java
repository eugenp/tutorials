package com.baeldung.compress;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;

public class GzipUtils {

    /**
     * Gzip a string.
     *
     * @param text
     * @return
     * @throws Exception
     */
    public static byte[] compress(String text) throws Exception {
        return GzipUtils.compress(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Gzip a byte array.
     *
     * @param body
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] body) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(baos)) {
            gzipOutputStream.write(body);
        }
        return baos.toByteArray();
    }

    /**
     * Decompress a Gzipped byte array to a String.
     *
     * @param body
     * @return
     * @throws IOException
     */
    public static String decompress(byte[] body) throws IOException {
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(body))) {
            return IOUtils.toString(gzipInputStream, StandardCharsets.UTF_8);
        }
    }
}
