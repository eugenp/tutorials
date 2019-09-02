package org.baeldung.spring.rest.compress;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils {

    /**
     * Gzip a string.
     *
     * @param text
     * @return
     * @throws Exception
     */
    public static byte[] compress(String text) throws Exception {
        return GzipUtils.compress(text.getBytes(Charsets.UTF_8));
    }

    /**
     * Gzip a byte array.
     *
     * @param body
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] body) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(output)) {
                gzipOutputStream.write(body);
            }
        } finally {
            output.close();
        }
        return output.toByteArray();
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
            return IOUtils.toString(gzipInputStream, Charsets.UTF_8);
        }
    }
}
